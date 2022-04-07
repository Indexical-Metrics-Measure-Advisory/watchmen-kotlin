package com.matrdata.storage

import com.matrdata.model.system.CompetitiveWorker
import com.sun.org.slf4j.internal.LoggerFactory
import sun.management.VMManagement
import java.lang.Thread.sleep
import java.lang.management.ManagementFactory
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.net.InetAddress
import java.util.*


class WorkerFirstDeclarationException : Exception {
	constructor()

	constructor(message: String) : super(message)

	constructor(message: String, cause: Throwable) : super(message, cause)

	constructor(cause: Throwable) : super(cause)
}

class WorkerCreationException(message: String) : Exception(message) {
}

/**
 * in seconds
 */
fun defaultHeartBeatInterval(): Int {
	return 60
}

fun defaultWorkerCreationRetryTimes(): Int {
	return 3
}

fun getCurrentProcessId(): Int {
	val runtime = ManagementFactory.getRuntimeMXBean()
	val jvm: Field = runtime.javaClass.getDeclaredField("jvm")
	jvm.isAccessible = true
	val management = jvm.get(runtime) as VMManagement
	val method: Method = management.javaClass.getDeclaredMethod("getProcessId")
	method.isAccessible = true
	return method.invoke(management) as Int
}

enum class CompetitiveWorkerShutdownSignal(val code: Int) {
	EXIT(1),
	EXCEPTION_RAISED(2)
}

typealias CompetitiveWorkerRestarter = () -> Unit
/**
 * first Int: data center id,
 * second Int: worker id
 */
typealias CompetitiveWorkerShutdownListener = (CompetitiveWorkerShutdownSignal, Int, Int, CompetitiveWorkerRestarter) -> Unit

abstract class CompetitiveWorkerIdGenerator(
	// will not check sanity of data center id here
	private val dataCenterId: Int = 0,
	// in seconds
	private val heartBeatInterval: Int = defaultHeartBeatInterval(),
	private val workerCreationRetryTimes: Int = defaultWorkerCreationRetryTimes(),
	private val handleShutdown: CompetitiveWorkerShutdownListener? = null
) {
	private var worker: CompetitiveWorker? = this.tryCreateWorker()
	private var firstDeclareTimes: Int = 0

	/**
	 * first declare me, implement me
	 */
	protected abstract fun firstDeclareMyself(worker: CompetitiveWorker)

	private fun createWorker(): CompetitiveWorker {
		// create a worker
		return try {
			this.firstDeclareTimes += 1
			val worker = CompetitiveWorker(
				ip = InetAddress.getLocalHost().hostAddress,
				processId = getCurrentProcessId().toString(),
				dataCenterId = this.dataCenterId,
				workerId = this.createWorkerId(),
				registeredAt = Date(),
				lastBeatAt = Date()
			)
			this.firstDeclareMyself(worker)
			worker
		} catch (e: WorkerFirstDeclarationException) {
			if (this.firstDeclareTimes <= this.workerCreationRetryTimes)
				this.createWorker()
			else {
				throw WorkerCreationException("Failed to create worker[dataCenterId=${this.dataCenterId}], reaches maximum retry times[${this.workerCreationRetryTimes}].")
			}
		}
	}

	private fun tryCreateWorker(): CompetitiveWorker {
		this.firstDeclareTimes = 0
		this.worker = this.createWorker()
		this.firstDeclareTimes = 0
		// start heart beat
		this.heartBeat()
		return this.worker!!
	}

	private fun randomWorkerId(): Int {
		return (Math.random() * 1024).toInt()
	}

	/**
	 * acquire used worker ids, implement me. returns used worker ids
	 */
	protected abstract fun acquireAliveWorkerIds(): List<Int>

	private fun createWorkerId(): Int {
		val aliveWorkerIds = this.acquireAliveWorkerIds()
		// random a worker id, return it when it is not used
		var newWorkerId = this.randomWorkerId()
		while (aliveWorkerIds.contains(newWorkerId)) {
			newWorkerId = this.randomWorkerId()
		}
		return newWorkerId
	}

	/**
	 * declare me is alive, implement me
	 */
	protected abstract fun declareMyself(worker: CompetitiveWorker)

	private fun heartBeat() {
		when (this.worker) {
			null -> {}
			else -> {
				try {
					while (true) {
						sleep(this.heartBeatInterval * 1000L)
						this.declareMyself(this.worker!!)
					}
					// heart beat stopped with no exception, release signal
					@Suppress("UNREACHABLE_CODE")
					this.handleShutdown?.let {
						it(
							CompetitiveWorkerShutdownSignal.EXIT,
							this.worker!!.dataCenterId!!, this.worker!!.workerId!!
						) { this.tryCreateWorker() }
					}
				} catch (e: Exception) {
					LoggerFactory.getLogger(javaClass).error("Error occurred in worker heart beat.", e)
					this.handleShutdown?.let {
						it(
							CompetitiveWorkerShutdownSignal.EXCEPTION_RAISED,
							this.worker!!.dataCenterId!!, this.worker!!.workerId!!
						) { this.tryCreateWorker() }
					}
				} finally {
					// release in -memory worker, will raise exception only if somebody calls me later
					this.worker = null
					LoggerFactory.getLogger(javaClass)
						.warn("Competitive worker id generator[${this.worker}] heart beat stopped.")
				}
			}
		}
	}

	/**
	 * generate snowflake worker id
	 */
	fun generate(): Int {
		return this.worker!!.workerId!!
	}
}

/**
 * create a worker id generator which delegate to given competitive generator
 */
fun competitiveWorkerId(generator: CompetitiveWorkerIdGenerator): WorkerIdGenerator {
	return { generator.generate() }
}

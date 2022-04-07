package com.matrdata.storage


class Snowflake(
    /** 0 - 3 */
    private val dataCenterId: Long,
    /** 0 - 1023 */
    private val generatorWorkerId: () -> Int
) {
    private val workerId: Long
    private var sequence: Long
    private var lastTimestamp: Long

    companion object {
        const val TWEPOCH: Long = 1420041600000

        // 0 - 3, 4 data centers maximum
        private const val DATACENTER_ID_BITS: Int = 2

        // 0 - 1023, 1024 workers maximum per data center
        private const val WORKER_ID_BITS: Int = 10

        // 0 - 1023, 1024 sequence numbers per millisecond
        private const val SEQUENCE_BITS: Int = 10

        // 2 ** 10 - 1 0b1111111111
        const val MAX_WORKER_ID: Int = -1 xor (-1 shl WORKER_ID_BITS)
        const val MAX_DATACENTER_ID: Int = -1 xor (-1 shl DATACENTER_ID_BITS)
        const val MAX_SEQUENCE: Long = (-1 xor (-1 shl SEQUENCE_BITS)).toLong()
        const val WORKER_ID_SHIFT: Int = SEQUENCE_BITS
        const val DATACENTER_ID_SHIFT: Int = SEQUENCE_BITS + WORKER_ID_BITS
        const val TIMESTAMP_LEFT_SHIFT: Int = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS
    }

    init {
        if (this.dataCenterId > MAX_DATACENTER_ID || this.dataCenterId < 0) {
            throw StorageException("Data center id invalid, it must be between [0, ${MAX_DATACENTER_ID}] and passed by [${this.dataCenterId}].")
        }

        // compute worker id
        val workerId = this.generatorWorkerId().toLong()
        // sanity check
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw StorageException(
                "Worker id invalid, it must be between [0, ${MAX_WORKER_ID}] and passed by [${workerId}]."
            )
        }

        // initialize
        this.workerId = workerId
        this.sequence = 0
        this.lastTimestamp = -1
    }

    private fun generateTimestamp(): Long {
        return System.currentTimeMillis()
    }


    private fun acquireNextMillisecond(lastTimestamp: Long): Long {
        var timestamp = generateTimestamp()

        while (timestamp <= lastTimestamp) {
            timestamp = generateTimestamp()
        }

        return timestamp
    }

    fun nextId(): Long {
        var timestamp = generateTimestamp()

        if (timestamp < this.lastTimestamp) {
            // incorrect timestamp
            // raise InvalidSystemClockException
            // use current time stamp
            timestamp = this.lastTimestamp
        }

        if (timestamp == this.lastTimestamp) {
            // exactly in same timestamp, increase sequence
            // and increase timestamp when sequence reaches the max value
            this.sequence = (this.sequence + 1) and MAX_SEQUENCE
            if (this.sequence == 0L) {
                timestamp = acquireNextMillisecond(this.lastTimestamp)
                this.lastTimestamp = timestamp
            }
        } else {
            // already beyonds in - memory timestamp, reset in-memory
            this.sequence = 0
            this.lastTimestamp = timestamp
        }

        return (((timestamp - TWEPOCH) shl TIMESTAMP_LEFT_SHIFT)
                or (this.dataCenterId shl DATACENTER_ID_SHIFT)
                or (this.workerId shl WORKER_ID_SHIFT)
                or this.sequence)
    }
}
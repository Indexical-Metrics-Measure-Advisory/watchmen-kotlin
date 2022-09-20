package com.matrdata.watchmen.model.admin

import com.matrdata.watchmen.model.common.DataModel
import com.matrdata.watchmen.model.common.EnumId
import com.matrdata.watchmen.model.common.FactorId
import com.matrdata.watchmen.utils.assert
import com.matrdata.watchmen.utils.throwIfFalse

enum class FactorType(val code: String) {
	SEQUENCE("sequence"),

	NUMBER("number"),
	UNSIGNED("unsigned"),  // 0 & positive

	TEXT("text"),

	// address
	ADDRESS("address"),
	CONTINENT("continent"),
	REGION("region"),
	COUNTRY("country"),
	PROVINCE("province"),
	CITY("city"),
	DISTRICT("district"),
	ROAD("road"),
	COMMUNITY("community"),
	FLOOR("floor"),
	RESIDENCE_TYPE("residence-type"),
	RESIDENTIAL_AREA("residential-area"),

	// contact electronic
	EMAIL("email"),
	PHONE("phone"),
	MOBILE("mobile"),
	FAX("fax"),

	// date time related
	DATETIME("datetime"),  // YYYY-MM-DD HH:mm:ss
	FULL_DATETIME("full-datetime"),  // YYYY-MM-DD HH:mm:ss.SSS
	DATE("date"),  // YYYY-MM-DD
	TIME("time"),  // HH:mm:ss
	YEAR("year"),  // 4 digits
	HALF_YEAR("half-year"),  // 1: first half, 2: second half
	QUARTER("quarter"),  // 1 - 4
	MONTH("month"),  // 1 - 12
	HALF_MONTH("half-month"),  // 1: first half, 2: second half
	TEN_DAYS("ten-days"),  // 1, 2, 3
	WEEK_OF_YEAR("week-of-year"),  // 0 (the partial week that precedes the first Sunday of the year) - 53 (leap year)
	WEEK_OF_MONTH("week-of-month"),  // 0 (the partial week that precedes the first Sunday of the year) - 5
	HALF_WEEK("half-week"),  // 1: first half, 2: second half
	DAY_OF_MONTH("day-of-month"),  // 1 - 31, according to month/year
	DAY_OF_WEEK("day-of-week"),  // 1 (Sunday) - 7 (Saturday)
	DAY_KIND("day-kind"),  // 1: workday, 2: weekend, 3: holiday
	HOUR("hour"),  // 0 - 23
	HOUR_KIND("hour-kind"),  // 1: work time, 2: off hours, 3: sleeping time
	MINUTE("minute"),  // 0 - 59
	SECOND("second"),  // 0 - 59
	MILLISECOND("millisecond"),  // 0 - 999
	AM_PM("am-pm"),  // 1, 2

	// individual
	GENDER("gender"),
	OCCUPATION("occupation"),
	DATE_OF_BIRTH("date-of-birth"),  // YYYY-MM-DD
	AGE("age"),
	ID_NO("id-no"),
	RELIGION("religion"),
	NATIONALITY("nationality"),

	// organization
	BIZ_TRADE("biz-trade"),
	BIZ_SCALE("biz-scale"),

	BOOLEAN("boolean"),

	ENUM("enum"),

	OBJECT("object"),
	ARRAY("array")
}

class DateTimeFactorValuesValidator(private val min: Int, private val max: Int) {
	fun isValidOrThrow(value: Int, invalidMessage: (validRange: String) -> String): Boolean {
		return value.assert {
			this in min..max
		}.throwIfFalse {
			IllegalArgumentException(invalidMessage("[$min .. $max]"))
		}
	}
}

object DateTimeFactorValueValidators {
	val HALF_YEAR = DateTimeFactorValuesValidator(1, 2)
	val QUARTER = DateTimeFactorValuesValidator(1, 4)
	val MONTH = DateTimeFactorValuesValidator(1, 12)
	val HALF_MONTH = DateTimeFactorValuesValidator(1, 2)
	val TEN_DAYS = DateTimeFactorValuesValidator(1, 3)
	val WEEK_OF_YEAR = DateTimeFactorValuesValidator(0, 53)
	val WEEK_OF_MONTH = DateTimeFactorValuesValidator(0, 5)
	val HALF_WEEK = DateTimeFactorValuesValidator(1, 2)
	val DAY_OF_MONTH = DateTimeFactorValuesValidator(1, 31)
	val DAY_OF_WEEK = DateTimeFactorValuesValidator(1, 7)
	val DAY_KIND = DateTimeFactorValuesValidator(1, 3)
	val HOUR = DateTimeFactorValuesValidator(0, 23)
	val HOUR_KIND = DateTimeFactorValuesValidator(1, 3)
	val MINUTE = DateTimeFactorValuesValidator(0, 59)
	val SECOND = DateTimeFactorValuesValidator(0, 59)
	val MILLISECOND = DateTimeFactorValuesValidator(0, 999)
	val AM_PM = DateTimeFactorValuesValidator(1, 3)
}

enum class FactorIndexGroup(val code: String) {
	INDEX_1("i-1"),
	INDEX_2("i-2"),
	INDEX_3("i-3"),
	INDEX_4("i-4"),
	INDEX_5("i-5"),
	INDEX_6("i-6"),
	INDEX_7("i-7"),
	INDEX_8("i-8"),
	INDEX_9("i-9"),
	INDEX_10("i-10"),
	UNIQUE_INDEX_1("u-1"),
	UNIQUE_INDEX_2("u-2"),
	UNIQUE_INDEX_3("u-3"),
	UNIQUE_INDEX_4("u-4"),
	UNIQUE_INDEX_5("u-5"),
	UNIQUE_INDEX_6("u-6"),
	UNIQUE_INDEX_7("u-7"),
	UNIQUE_INDEX_8("u-8"),
	UNIQUE_INDEX_9("u-9"),
	UNIQUE_INDEX_10("u-10")
}

enum class FactorEncryptMethod(val code: String) {
	NONE("none"),
	AES256_PKCS5_PADDING("AES256-PKCS5-PADDING"),
	MD5("MD5"),
	SHA256("SHA256"),
	MASK_MAIL("MASK-MAIL"),
	MASK_CENTER_3("MASK-CENTER-3"),
	MASK_CENTER_5("MASK-CENTER-5"),
	MASK_LAST_3("MASK-LAST-3"),
	MASK_LAST_6("MASK-LAST-6"),
	MASK_DAY("MASK-DAY"),
	MASK_MONTH("MASK-MONTH"),
	MASK_MONTH_DAY("MASK-MONTH-DAY")
}

data class Factor(
	var factorId: FactorId? = null,
	var type: FactorType? = null,
	var name: String? = null,
	var enumId: EnumId? = null,
	var label: String? = null,
	var description: String? = null,
	var defaultValue: String? = null,
	var flatten: Boolean? = false,
	var indexGroup: FactorIndexGroup? = null,
	var encrypt: FactorEncryptMethod? = null,
	var precision: String? = null,
) : DataModel
	

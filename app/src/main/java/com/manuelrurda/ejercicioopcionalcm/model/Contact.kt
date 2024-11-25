package com.manuelrurda.ejercicioopcionalcm.model

class Contact(private val vcardString: String){
    var name: String = ""
        get() = field
    var phone: String = ""
        get() = field
    var email: String = ""
        get() = field

    companion object{
        val fields = arrayOf("FN:", "TEL:", "EMAIL:")
    }

    init {
        val values = parseVCardString()
        this.name = values[0]
        this.phone = values[1]
        this.email = values[2]
    }

    private fun parseVCardString(): List<String> {
        val values = fields.map {field ->
            val startIndex = vcardString.indexOf(field) + field.length
            val value:String = vcardString.substring(
                startIndex,
                vcardString.indexOf('\n', startIndex))

            value.ifEmpty { "" }
        }

        return values
    }
}
package nl.twinkelmc.core.utils

//import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.bukkit.Bukkit

class MessageFormatter {

    private val messageListNL = hashMapOf<String,String>()
    private val messageListEN = hashMapOf<String,String>()
    private val messageListDE = hashMapOf<String,String>()
    private val messageListFR = hashMapOf<String,String>()

//    fun read() {
//        csvReader().open("plugins/TMC-Core/NL.csv") {
//            readAllAsSequence().forEach { row: List<String> ->
//                messageListNL[row[0]] = row[1]
//            }
//        }
//    }

    fun getMessage(lang: String, msg: String): String {
        when (lang) {
            "NL" -> return if (messageListNL.containsKey(msg)) messageListNL[msg]!!.formatMSG() else "Message not supported".formatMSG()
            "EN" -> return if (messageListEN.containsKey(msg)) messageListEN[msg]!!.formatMSG() else "Message not supported".formatMSG()
            "DE" -> return if (messageListDE.containsKey(msg)) messageListDE[msg]!!.formatMSG() else "Message not supported".formatMSG()
            "FR" -> return if (messageListFR.containsKey(msg)) messageListFR[msg]!!.formatMSG() else "Message not supported".formatMSG()
        }
        return "Language not supported".formatMSG()
    }

    private fun String.formatMSG(): String {
        return "§c§lTwinkel§r§cMC §8► §7$this"
    }

    private fun String.replaceMSG(from: String, to: String): String {
        return this.replace(from, "§e$to§7")
    }

    private fun getEnabled(lang: String): String {
        when (lang) {
            "NL" -> return "ingeschakeld"
            "EN" -> return "enabled"
            "DE" -> return "enabled"
            "FR" -> return "enabled"
        }
        return "enabled"
    }

    private fun getDisabled(lang: String): String {
        when (lang) {
            "NL" -> return "uitgeschakeld"
            "EN" -> return "disabled"
            "DE" -> return "disabled"
            "FR" -> return "disabled"
        }
        return "disabled"
    }

    fun getState(lang: String, state: Boolean): String {
        return if (state) {
            "§a" + getEnabled(lang)
        } else {
            "§c" + getDisabled(lang)
        }
    }

    fun getMSGFormat(lang: String, vararg args: String?): String {
        val msg = args[0]
        return if (msg != null) {
            var message = getMessage(lang, msg)
            for (i in 1 until args.size) {
                if (i % 2 == 0)
                    message = message.replaceMSG(args[i-1]!!, args[i]!!)
            }
            message
        } else {
            "Severe error with message format".formatMSG()
        }

    }

}
package com.superkooka.oerthyon.factionitems.utils

import com.google.common.io.Files
import org.apache.commons.lang.Validate
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.YamlConfiguration
import java.io.*
import java.nio.charset.Charset

/**
 * @author DrkMatr1984 for Java version (Superkooka vor Kotlin)
 * <https://gist.github.com/DrkMatr1984/8523072>
 */
class Utf8YamlConfiguration : YamlConfiguration() {

    @Throws(IOException::class, InvalidConfigurationException::class)
    override fun load(stream: InputStream) {
        Validate.notNull(stream, "Stream cannot be null")
        val reader = InputStreamReader(stream, UTF8_CHARSET)
        val builder = StringBuilder()
        val input = BufferedReader(reader)
        try {
            var line: String?
            while (input.readLine().also { line = it } != null) {
                builder.append(line)
                builder.append('\n')
            }
        } finally {
            input.close()
        }
        loadFromString(builder.toString())
    }

    @Throws(IOException::class)
    override fun save(file: File) {
        Validate.notNull(file, "File cannot be null")
        Files.createParentDirs(file)
        val data = saveToString()
        val stream = FileOutputStream(file)
        val writer = OutputStreamWriter(stream, UTF8_CHARSET)
        try {
            writer.write(data)
        } finally {
            writer.close()
        }
    }

    companion object {
        var UTF8_CHARSET = Charset.forName("UTF-8")
    }
}
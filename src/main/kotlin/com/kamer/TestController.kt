package com.kamer

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import org.apache.tika.config.TikaConfig
import org.apache.tika.detect.DefaultDetector
import org.apache.tika.io.TikaInputStream
import org.apache.tika.metadata.Metadata
import org.apache.tika.metadata.TikaCoreProperties
import org.apache.tika.parser.AutoDetectParser
import org.apache.tika.parser.ParseContext
import org.apache.tika.parser.Parser
import org.apache.tika.sax.BodyContentHandler
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * Created on October, 2021
 *
 * @author kamer
 */
@ExecuteOn(TaskExecutors.IO)
@Controller("attachments")
class TestController {

    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Post("/upload")
    fun create(file: CompletedFileUpload): HttpResponse<Any> {


        val savedFile = File("${UUID.randomUUID()}")
        savedFile.createNewFile()
        val path = Paths.get(savedFile.absolutePath)
        Files.write(path, file.bytes)


        // This is how Tika docs says.
        // https://tika.apache.org/2.1.0/detection.html#Ways_of_triggering_Detection
        val metadata = Metadata()
        metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, savedFile.toString())
        val inputStream = TikaInputStream.get(savedFile.readBytes())
        val mimeType = TikaConfig().detector.detect(inputStream, metadata).toString()

        ///////////////////////////////////////////////////

        // Another way
        /*val metadata = Metadata()
        metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, savedFile.toString())
        val inputStream = TikaInputStream.get(savedFile.readBytes())
        val mimeType = DefaultDetector().detect(inputStream, metadata).toString()*/

        //////////////////////////////////////////////////

        // Another way

        /*val fileStream = FileInputStream(savedFile)
        val parser = AutoDetectParser()
        val metadata = Metadata()
        metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, savedFile.toPath().toString())
        val handler = BodyContentHandler()

        val parseContext = ParseContext()
        parser.parse(fileStream, handler, metadata, parseContext)
        val mimeType = metadata["Content-Type"]*/




        // org.apache.tika.parser.microsoft.OfficeParser
        /*val stream = TikaInputStream.get(savedFile.inputStream())
        val metadata = Metadata()
        metadata.set(TikaCoreProperties.RESOURCE_NAME_KEY, savedFile.name)
        val mediaTypes = DefaultDetector().detect(stream, metadata)
        print(mediaTypes.type)*/


        return HttpResponse.ok(Type(mimeType))

    }

    @Introspected
    data class Type(val type: String)

}
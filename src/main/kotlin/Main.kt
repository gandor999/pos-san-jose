import net.glxn.qrgen.javase.QRCode
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val stream: ByteArrayOutputStream = QRCode
        .from("Hello World")
        .withSize(250, 250)
        .stream()
    val bis = ByteArrayInputStream(stream.toByteArray())

    val outputFile = File("modified_image.jpg")
    ImageIO.write(ImageIO.read(bis), "jpg", outputFile)

//    println(ImageIO.read(bis))
}
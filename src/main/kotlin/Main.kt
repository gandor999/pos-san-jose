import java.io.File
import javax.imageio.ImageIO


fun main(args: Array<String>) {
    val qrCode = createQRCode("itemName: Sample_Item; price: PHP_100", "Sample Item", "Price: ₱100")

    val outputFile = File("modified_image.jpg")
    ImageIO.write(qrCode, "jpg", outputFile)

    val sampleImageFromInternet = File("sample-bardcode-image-from-internet.jpg")
    println(decodeQRCode(ImageIO.read(sampleImageFromInternet)))
}
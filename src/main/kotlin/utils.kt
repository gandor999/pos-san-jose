import com.google.zxing.*
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.BitMatrix
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.max

/**
 * Creates a QRCode with text on top and bottom
 *
 * @param dataInQRCode the data we would like to provide into the QRCode
 * @param topText the text we would like to see on top of the QRCode
 * @param bottomText the text we would like to see below the QRCode
 *
 * @author https://www.baeldung.com/java-generating-barcodes-qr-codes
 */
fun createQRCode(dataInQRCode: String, topText: String, bottomText: String): BufferedImage {
    val barcodeWriter = QRCodeWriter()
    val matrix: BitMatrix = barcodeWriter.encode(dataInQRCode, BarcodeFormat.QR_CODE, 200, 200)
    val matrixWidth = matrix.width
    val matrixHeight = matrix.height

    val image = BufferedImage(matrixWidth, matrixHeight, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.fillRect(0, 0, matrixWidth, matrixHeight)
    graphics.color = Color.BLACK

    for (i in 0..<matrixWidth) {
        for (j in 0..<matrixHeight) {
            if (matrix[i, j]) {
                graphics.fillRect(i, j, 1, 1)
            }
        }
    }

    val fontMetrics = graphics.fontMetrics
    val topTextWidth = fontMetrics.stringWidth(topText)
    val bottomTextWidth = fontMetrics.stringWidth(bottomText)
    val finalWidth =
        (max(matrixWidth.toDouble(), max(topTextWidth.toDouble(), bottomTextWidth.toDouble())) + 1).toInt()
    val finalHeight = matrixHeight + fontMetrics.height + fontMetrics.ascent + 1

    val finalImage = BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_RGB)
    val finalGraphics = finalImage.createGraphics()
    finalGraphics.fillRect(0, 0, finalWidth, finalHeight)
    finalGraphics.color = Color.BLACK

    finalGraphics.drawImage(image, (finalWidth - matrixWidth) / 2, fontMetrics.ascent + 2, null)
    finalGraphics.drawString(topText, (finalWidth - topTextWidth) / 2, fontMetrics.ascent + 20)
    finalGraphics.drawString(
        bottomText,
        ((finalWidth - bottomTextWidth) / 4) - 5,
        finalHeight - fontMetrics.descent - 20
    )

    return finalImage
}

/**
 * Decodes a QRCode image
 *
 * @param image the image we would like to decode in BufferedImage
 *
 * @author chatgpt
 */
fun decodeQRCode(image: BufferedImage): String? {
    val source = BufferedImageLuminanceSource(image)
    val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
    val reader = MultiFormatReader()

    return try {
        val result: Result = reader.decode(binaryBitmap)
        result.text
    } catch (e: ReaderException) {
        null
    }
}
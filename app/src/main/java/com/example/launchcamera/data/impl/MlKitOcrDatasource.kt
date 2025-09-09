package com.example.launchcamera.data.impl

import android.annotation.SuppressLint
import androidx.camera.core.ImageProxy
import com.example.launchcamera.data.datasource.OcrDatasource
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@SuppressLint("UnsafeOptInUsageError")
class MlKitOcrDatasource @Inject constructor() : OcrDatasource {

    override suspend fun recognizeText(imageProxy: ImageProxy): Result<Text> = try {
        val recognizedText = processImageWithMlKit(imageProxy)
        Result.success(recognizedText)
    } catch (e: Exception) {
        Result.failure(e)
    }

    private suspend fun processImageWithMlKit(imageProxy: ImageProxy): Text {
        return imageProxy.image?.let { mediaImage ->
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

            suspendCancellableCoroutine<Text> { continuation ->
                recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        continuation.resume(visionText)
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
                continuation.invokeOnCancellation {
                    imageProxy.close()
                }
            }
        } ?: run {
            imageProxy.close()
            throw IllegalArgumentException("ImageProxy no contain image valid.")
        }
    }
}
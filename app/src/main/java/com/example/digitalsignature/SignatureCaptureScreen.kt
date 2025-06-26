package com.example.digitalsignature

import android.graphics.Bitmap
import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.digitalsignature.ui.theme.DigitalSignatureTheme

@Composable
fun SignatureCaptureScreen() {
    // Holds the path drawn by the user
    var path by remember { mutableStateOf(Path()) }
    // Holds the captured signature as a bitmap
    var signatureBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Please sign below with changes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // The area where the user can draw.
        // We use BoxWithConstraints to get the size of the drawing area,
        // which is crucial for creating the bitmap.
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
                .padding(4.dp)
        ) {
            val boxWidth = this.maxWidth
            val boxHeight = this.maxHeight

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures { change, _ ->
                            // Add the new line segment to the existing path
                            path.moveTo(change.previousPosition.x, change.previousPosition.y)
                            path.lineTo(change.position.x, change.position.y)

                            Log.e("Test Drawing ","Drawing")
                            Log.e("Test Drawing ","Drawing")
                            path = Path().apply {
                                addPath(path)
                            }

                        // Create a new Path object that is a copy of the updated path.
                            // This is necessary to trigger a recomposition.

                        }
                       /* detectDragGestures { change, _ ->
                            path.moveTo(change.previousPosition.x, change.previousPosition.y)
                            path.lineTo(change.position.x, change.position.y)
                            // Trigger a recomposition to draw the new path segment
                            path = Path()
                        }*/
                    }
            ) {
                drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(
                        width = 4.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }

            // Control buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
            ) {
                // Clear button
                Button(onClick = {
                    path = Path() // Reset the path
                    signatureBitmap = null // Clear the captured image
                }) {
                    Text("Clear ")
                }

                // Save button
                Button(onClick = {
                    // Create a bitmap with the size of the drawing area
                    val bitmap = Bitmap.createBitmap(
                        boxWidth.value.toInt(),
                        boxHeight.value.toInt(),
                        Bitmap.Config.ARGB_8888
                    )
                    val canvas = android.graphics.Canvas(bitmap)

                    // Draw the path onto the bitmap's canvas
                    canvas.drawPath(path.asAndroidPath(), Paint().apply {
                        color = android.graphics.Color.BLACK
                        strokeWidth = 3f
                        style = Paint.Style.STROKE
                        strokeJoin = Paint.Join.ROUND
                        strokeCap = Paint.Cap.ROUND
                    })
                    signatureBitmap = bitmap.asImageBitmap()
                }) {
                    Text("Save")
                }
            }
        }

        // Display the captured signature
        if (signatureBitmap != null) {
            Text(
                text = "Captured Signature: ",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
            Image(
                bitmap = signatureBitmap!!,
                contentDescription = "The captured signature",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.White)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignatureCaptureScreenPreview() {

    DigitalSignatureTheme {
        SignatureCaptureScreen()
    }

    DigitalSignatureTheme {
        SignatureCaptureScreen()
    }

    DigitalSignatureTheme {
        SignatureCaptureScreen()
    }

    DigitalSignatureTheme {
        SignatureCaptureScreen()
    }

    DigitalSignatureTheme {
        SignatureCaptureScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun SignatureCaptureScreenPreview3() {
    DigitalSignatureTheme {
        SignatureCaptureScreen()
    }
}
@Preview(showBackground = true)
@Composable
private fun SignatureCaptureScreenPreview33() {
    DigitalSignatureTheme {
        SignatureCaptureScreen()
    }
}

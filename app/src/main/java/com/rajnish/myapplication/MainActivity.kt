package com.rajnish.myapplication

import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.hardware.usb.UsbConstants
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.print.PrintHelper
import com.rajnish.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val printerReceiver = PrinterReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Register the printer receiver when the activity is resumed
        val filter = IntentFilter().apply {
            addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
            addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        }
        registerReceiver(printerReceiver, filter)

        // Example button to trigger photo printing
        val printButton: Button = findViewById(R.id.print)
        printButton.setOnClickListener {
            // Call the printPhoto() function to start printing the photo
            printPhoto()
        }
    }

    private fun printPhoto() {
        val usbManager = getSystemService(UsbManager::class.java)
        val usbDeviceList = usbManager?.deviceList

        // Check if any USB devices are connected
        if (usbDeviceList?.isNotEmpty() == true) {
            // Find the first USB printer device
            val usbDevice: UsbDevice? = usbDeviceList.values.firstOrNull { isPrinter(it) }

            if (usbDevice != null) {
                // Load the photo to be printed from a resource, file, or any other source
                val photoBitmap = BitmapFactory.decodeResource(resources, R.drawable.a)

                // Print the photo using your desired printing logic
                // Replace the code below with your actual printing logic
                val printHelper = PrintHelper(this)
                printHelper.scaleMode = PrintHelper.SCALE_MODE_FIT
                printHelper.printBitmap("MyPhotoPrint", photoBitmap)
            } else {
                Toast.makeText(this, "No printer connected", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No USB devices connected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the printer receiver when the activity is destroyed
        unregisterReceiver(printerReceiver)
    }

    private fun isPrinter(usbDevice: UsbDevice): Boolean {
        // Check if the USB device is a printer based on its device class, subclass, and protocol
        // Implement your own logic based on the specific USB printer criteria
        // Example:
        return usbDevice.deviceClass == UsbConstants.USB_CLASS_PRINTER
    }
}







package com.rajnish.myapplication
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbConstants
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Build
import androidx.core.app.NotificationCompat
class PrinterReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (UsbManager.ACTION_USB_DEVICE_ATTACHED == action) {
            val usbDevice = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
            if (isPrinter(usbDevice)) {
                // Printer connected, display a notification
                showPrinterConnectedNotification(context, usbDevice?.deviceName)
            }
        } else if (UsbManager.ACTION_USB_DEVICE_DETACHED == action) {
            // Printer disconnected, handle it accordingly
        }
    }

    private fun isPrinter(usbDevice: UsbDevice?): Boolean {
        // Implement your logic here to determine if the USB device is a printer
        return usbDevice?.deviceClass == UsbConstants.USB_CLASS_PRINTER
    }

    private fun showPrinterConnectedNotification(context: Context, deviceName: String?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "printer_connection_channel"
        val channelName = "Printer Connection"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Printer Connected")
            .setContentText("Printer: $deviceName")
            .setSmallIcon(R.drawable.a)

        notificationManager.notify(1, notificationBuilder.build())
    }
}

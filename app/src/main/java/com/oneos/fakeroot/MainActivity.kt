package com.oneos.fakeroot

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.ViewGroup
import android.widget.*
import org.json.JSONObject

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("simulator", MODE_PRIVATE)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 55, 40, 30)
            setBackgroundColor(Color.rgb(245, 248, 255))
        }
        fun label(text: String): TextView = TextView(this).apply {
            this.text = text
            textSize = 19f
            setTextColor(Color.rgb(25, 35, 65))
            setPadding(0, 14, 0, 14)
        }
        root.addView(label("OneOS FakeRoot — SIMULATION ONLY").apply { textSize = 23f })
        val toggle = Switch(this).apply {
            text = "Enable fake device root"
            isChecked = prefs.getBoolean("enabled", true)
        }
        root.addView(toggle)
        root.addView(label("Device identity"))
        val device = Spinner(this)
        val devices = listOf("Samsung Galaxy S26 Ultra", "Google Pixel (simulated)", "Original device")
        device.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, devices)
        device.setSelection(prefs.getInt("device", 0))
        root.addView(device)
        root.addView(label("Root provider"))
        val provider = Spinner(this)
        val providers = listOf("SuperSU", "Magisk", "Not rooted")
        provider.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, providers)
        provider.setSelection(prefs.getInt("provider", 0))
        root.addView(provider)
        val status = label("")
        root.addView(status)
        fun refresh() {
            val enabled = toggle.isChecked
            device.isEnabled = enabled
            provider.isEnabled = enabled
            val name = if (enabled) devices[device.selectedItemPosition] else "Original device"
            val su = if (enabled) providers[provider.selectedItemPosition] else "Not rooted"
            status.text = "SIMULATED PROFILE\nDevice: $name\nRoot: $su\n\nActual Android device and root status remain unchanged."
            prefs.edit().putBoolean("enabled", enabled)
                .putInt("device", device.selectedItemPosition)
                .putInt("provider", provider.selectedItemPosition).apply()
        }
        toggle.setOnCheckedChangeListener { _, _ -> refresh() }
        val listener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) { refresh() }
        }
        device.onItemSelectedListener = listener
        provider.onItemSelectedListener = listener
        val test = Button(this).apply {
            text = "Test fake SuperSU permission"
            setOnClickListener {
                android.app.AlertDialog.Builder(this@MainActivity)
                    .setTitle("SuperSU — simulation")
                    .setMessage("Demo application requests simulated superuser access. No actual root permission is granted.")
                    .setPositiveButton("Simulate grant") { _, _ -> Toast.makeText(this@MainActivity, "Simulated grant only", Toast.LENGTH_SHORT).show() }
                    .setNegativeButton("Deny", null).show()
            }
        }
        root.addView(test)
        val export = Button(this).apply {
            text = "Show OneOS configuration"
            setOnClickListener {
                val config = JSONObject().put("fakeDeviceRoot", toggle.isChecked)
                    .put("device", if (device.selectedItemPosition == 0) "s26" else if (device.selectedItemPosition == 1) "pixel" else "original")
                    .put("rootProvider", if (provider.selectedItemPosition == 0) "supersu" else if (provider.selectedItemPosition == 1) "magisk" else "none")
                    .put("simulationOnly", true)
                android.app.AlertDialog.Builder(this@MainActivity)
                    .setTitle("Simulation configuration").setMessage(config.toString(2))
                    .setPositiveButton("OK", null).show()
            }
        }
        root.addView(export)
        val scroll = ScrollView(this).apply { addView(root) }
        setContentView(scroll)
        refresh()
    }
}

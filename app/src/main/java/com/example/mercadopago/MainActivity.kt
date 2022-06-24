package com.example.mercadopago

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mercadopago.databinding.ActivityMainBinding
import com.mercadopago.android.px.core.MercadoPagoCheckout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            MercadoPagoCheckout.Builder(
                "TEST-ad365c37-8012-4014-84f5-6c895b3f8e0a",
                "150216849-ceed1ee4-8ab9-4449-869f-f4a8565d386f"
            ).build().startPayment(this, 555)
        }
    }

    // Espera los resultados del checkout
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 555) {
            if (resultCode == RESULT_OK && data != null) {

                // Listo! El pago ya fue procesado por MP.
                val payment = data.getStringExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT)
                binding.txtResults.text = "Listo! El pago ya fue procesado por MP --> $payment"
            } else {
                if (data != null && data.hasExtra("mpException")) {
                    binding.txtResults.text = "Ha habido un problema con el pago"
                }
            }
        }
    }
}
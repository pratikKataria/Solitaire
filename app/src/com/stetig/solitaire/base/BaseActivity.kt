package com.stetig.solitaire.base

import android.util.Log
import android.view.View
import com.stetig.solitaire.R
import com.salesforce.androidsdk.rest.RestClient
import com.salesforce.androidsdk.ui.SalesforceActivity
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by Pratik Kataria on 18-11-2020.
 */
abstract class BaseActivity : SalesforceActivity(), View.OnClickListener {

    private var client: RestClient? = null

    abstract fun initViews()

    override fun onResume(client: RestClient?) {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        client!!.okHttpClient = client.okHttpClient
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

        this.client = client
    }

    override fun onClick(view: View) {
        Log.e("TAG", "onClick: button clicked")
        if (view.id == R.id.thinBackBtn) {
            onBackPressed()
        }
    }

    fun getRestClient(): RestClient? = client
}
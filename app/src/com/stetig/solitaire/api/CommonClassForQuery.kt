package com.stetig.solitaire.api

import android.app.Activity
import android.util.Log
import com.stetig.solitaire.data.*
import com.stetig.solitaire.utils.Utils
import com.google.gson.Gson
import com.salesforce.androidsdk.rest.ApiVersionStrings
import com.salesforce.androidsdk.rest.RestClient
import com.salesforce.androidsdk.rest.RestClient.AsyncRequestCallback
import com.salesforce.androidsdk.rest.RestRequest
import com.salesforce.androidsdk.rest.RestResponse

/**
 * Created by Pratik Katariya on 27-09-2020
 */
class CommonClassForQuery private constructor() {

    companion object {
        private var commonClassForQuery: CommonClassForQuery? = null
        private var activity: Activity? = null
        private var restClient: RestClient? = null

        @JvmStatic
        fun getInstance(mActivity: Activity?, mRestClient: RestClient?): CommonClassForQuery? {
            if (commonClassForQuery == null) {
                synchronized(CommonClassForQuery::class.java) { if (commonClassForQuery == null) commonClassForQuery = CommonClassForQuery() }
            }
            activity = mActivity
            restClient = mRestClient
            return commonClassForQuery
        }
    }

    public interface OnDataReceiveListener {
        fun onDataReceive(data: Any)
        fun onError(obj: String)
    }

    fun getAccountDetails(query: String, onDataReceiveListener: OnDataReceiveListener) {
        try {
            Log.e("common class for query", "getAccountDetails: $query")
            Utils.showProgressDialog(activity)
            val request = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(request, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val projects: AccountDetail = Gson().fromJson<AccountDetail>(response.asString(), AccountDetail::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: java.lang.Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (xe: Exception) {

        }
    }

    fun getApprovalTableDetails(query: String, onDataReceiveListener: OnDataReceiveListener) {
        try {
            Log.e("common class for query", "getApprovalTableDetails: $query")
            Utils.showProgressDialog(activity)
            val request = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(request, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val projects: ApprovalTable = Gson().fromJson<ApprovalTable>(response.asString(), ApprovalTable::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: java.lang.Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (xe: Exception) {

        }
    }

    fun getCampaignTableDetails(query: String, onDataReceiveListener: OnDataReceiveListener) {
        try {
            Log.e("common class for query", "getCampaignTableDetails: $query")
            Utils.showProgressDialog(activity)
            val request = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(request, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val projects: CampaignApproval = Gson().fromJson<CampaignApproval>(response.asString(), CampaignApproval::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
                override fun onError(exception: java.lang.Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (xe: Exception) {

        }
    }

    fun getCount(query: String, type: String?, onDataReceiveListener: OnDataReceiveListener) {
        Log.d(javaClass.name, "exampleQuery: 44 example query$query")
        try {
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    try {
                        Log.d(javaClass.name, "exampleQuery: 44 convertion $query")
                        val taskCount: TaskCount = Gson().fromJson(response.asString(), TaskCount::class.java)
                        when (type) {
                            Query.SITE_VISIT -> {
                                taskCount.type = Query.SITE_VISIT
                                sendDataToUIThread(onDataReceiveListener, taskCount)
                            }
                            Query.FOLLOW_UP -> {
                                taskCount.type = Query.FOLLOW_UP
                                sendDataToUIThread(onDataReceiveListener, taskCount)
                            }
                            Query.FACE_TO_FACE -> {
                                taskCount.type = Query.FACE_TO_FACE
                                sendDataToUIThread(onDataReceiveListener, taskCount)
                            }
                            Query.NEGOTIATION -> {
                                taskCount.type = Query.NEGOTIATION
                                sendDataToUIThread(onDataReceiveListener, taskCount)
                            }
                            Query.NEED_ANALYSIS -> {
                                taskCount.type = Query.NEED_ANALYSIS
                                sendDataToUIThread(onDataReceiveListener, taskCount)
                            }
                            Query.PROPOSAL -> {
                                taskCount.type = Query.PROPOSAL
                                sendDataToUIThread(onDataReceiveListener, taskCount)
                            }
                            Query.BIP -> {
                                taskCount.type = Query.BIP
                                sendDataToUIThread(onDataReceiveListener, taskCount)
                            }
                            Query.QUALIFICATION -> {
                                taskCount.type = Query.QUALIFICATION
                                sendDataToUIThread(onDataReceiveListener, taskCount)
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: java.lang.Exception) {
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getProjects(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        try {
            Log.e("common class for query", "getAccountDetails: $query")
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    try {
                        val projects: Projects = Gson().fromJson(response.asString(), Projects::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getProjectDetails(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        try {
            Log.e("common class for query", "getAccountDetails: $query")
            Utils.showProgressDialog(activity)
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val projects: ProjectDetail = Gson().fromJson(response.asString(), ProjectDetail::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getPublicURL(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        try {
            Log.e("common class for query", "getAccountDetails: $query")
            Utils.showProgressDialog(activity)
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val projects: ProjectLink = Gson().fromJson(response.asString(), ProjectLink::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getSearchDetail(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        try {
            Log.e("Query Processor", "getTimeLine:::  $query")
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    try {
                        val projects: Search = Gson().fromJson(response.asString(), Search::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getOpportunity(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        Log.e(javaClass.name, "getOpportunity: $query")
        Utils.showProgressDialog(activity)
        try {
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val projects: Opportunity = Gson().fromJson(response.asString(), Opportunity::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getAllManualTasks(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        Log.e(javaClass.name, "getOpportunity: $query")
        Utils.showProgressDialog(activity)
        try {
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val projects: ManualTaskListResponse = Gson().fromJson(response.asString(), ManualTaskListResponse::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getSitevisit(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        Log.e(javaClass.name, "getSitevisit: $query")
        Utils.showProgressDialog(activity)
        try {
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val projects: Sitevisit = Gson().fromJson(response.asString(), Sitevisit::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getApproval(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        Log.e(javaClass.name, "getApproval: $query")
        Utils.showProgressDialog(activity)
        try {
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val approvals: Approval = Gson().fromJson(response.asString(), Approval::class.java)
                        sendDataToUIThread(onDataReceiveListener, approvals)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getApprovalWithoutLoader(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        Log.e(javaClass.name, "getApproval: $query")
         try {
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                     try {
                        val approvals: Approval = Gson().fromJson(response.asString(), Approval::class.java)
                        sendDataToUIThread(onDataReceiveListener, approvals)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                     onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getCampaign(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        Log.e(javaClass.name, "getCampaign: $query")
        Utils.showProgressDialog(activity)
        try {
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val approvals: CampaignApproval = Gson().fromJson(response.asString(), CampaignApproval::class.java)
                        sendDataToUIThread(onDataReceiveListener, approvals)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getSourceChanegApproval(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        Log.e(javaClass.name, "getSourceChanegApproval: $query")
        Utils.showProgressDialog(activity)
        try {
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val approvals: SourceChangeApproval = Gson().fromJson(response.asString(), SourceChangeApproval::class.java)
                        sendDataToUIThread(onDataReceiveListener, approvals)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getCpCreationApproval(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        Log.e(javaClass.name, "getCpCreationApproval: $query")
        Utils.showProgressDialog(activity)
        try {
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val approvals: CpCreationApproval = Gson().fromJson(response.asString(), CpCreationApproval::class.java)
                        sendDataToUIThread(onDataReceiveListener, approvals)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getTimeLine(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        try {
            Log.e("common class for query", "getAccountDetails: $query")
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    try {
                        val projects: Timeline = Gson().fromJson(response.asString(), Timeline::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getOptyDetail(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        try {
            Log.e("common class for query", "getAccountDetails: $query")
            Utils.showProgressDialog(activity)
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val projects: Opportunity = Gson().fromJson(response.asString(), Opportunity::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getTask(query: String?, onDataReceiveListener: OnDataReceiveListener) {
        try {
            Log.e("common class for query", "getAccountDetails: $query")
            Utils.hideProgressDialog(activity)
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    Utils.hideProgressDialog(activity)
                    try {
                        val projects: Task = Gson().fromJson(response.asString(), Task::class.java)
                        sendDataToUIThread(onDataReceiveListener, projects)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {
                    Utils.hideProgressDialog(activity)
                    onDataReceiveListener.onError(exception.message!!)
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun sendDataToUIThread(onDataReceiveListener: OnDataReceiveListener, taskCount: Any?) {
        if (activity != null) {
            activity!!.runOnUiThread { onDataReceiveListener.onDataReceive(taskCount!!) }
        }
    }

    fun testAnyQuery(query: String?, type: String) {
        Log.e(javaClass.name, "exampleQuery: 44 " + " type ---- - -- " + type + "example query" + query)
        try {
            val restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(activity), query)
            restClient!!.sendAsync(restRequest, object : AsyncRequestCallback {
                override fun onSuccess(request: RestRequest, response: RestResponse) {
                    try {
                        Log.e(javaClass.name, "onSuccess: 126 ------------------------------------------------------------")
                        Log.e(javaClass.name, "onSuccess: 127 " + " type ---- - -- " + type + response.asString())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(exception: Exception) {}
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

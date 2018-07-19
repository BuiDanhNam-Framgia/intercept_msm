package framgia.vn.interceptmsm


interface OnReceiveSMSListener {
    fun onSuccess(code: String)
    fun onError(ex: Exception)
    fun onComplete()
}
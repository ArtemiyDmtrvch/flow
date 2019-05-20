package ru.impression.flow_architecture

import android.arch.lifecycle.ViewModel
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

abstract class FlowFragment<F : Flow, S : Any>(
    override val flowClass: Class<F>,
    override val flowViewModelClass: Class<out ViewModel>? = null
) : Fragment(), FlowView<F, S> {

    override var flow: Flow? = null

    override var flowViewModel: IFlowViewModel<F>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FrameLayout(activity!!)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachToFlow()
    }

    protected fun setView(layoutResId: Int) =
        setView(View.inflate(activity!!, layoutResId, null))

    protected open fun setView(view: View) {
        this.view.apply {
            if (this is ViewGroup) {
                removeAllViews()
                addView(view)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        isActive = false
        super.onConfigurationChanged(newConfig)
    }

    override fun onDestroyView() {
        detachFromFlow()
        super.onDestroyView()
    }
}
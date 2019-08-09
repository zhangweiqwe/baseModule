package cn.wsgwz.basemodule.widgets.progressActivity

import android.view.View

class Config private constructor(builder: Builder) {

    internal var description: CharSequence? = null
        private set
    internal var skipIds: ArrayList<Int>? = null
        private set
    internal var buttonClickListener: View.OnClickListener? = null
        private set

    init {
        description = builder.description
        skipIds = builder.skipIds
        buttonClickListener = builder.buttonClickListener
    }

    class Builder {
        internal var description: CharSequence? = null
            private set
        internal var skipIds: ArrayList<Int>? = null
            private set
        internal var buttonClickListener: View.OnClickListener? = null
            private set


        fun description(description: CharSequence?): Builder {
            this.description = description
            return this
        }


        fun skipIds(skipIds: ArrayList<Int>?): Builder {
            this.skipIds = skipIds
            return this
        }


        fun buttonClickListener(buttonClickListener: View.OnClickListener?): Builder {
            this.buttonClickListener = buttonClickListener
            return this
        }

        fun build() = Config(this)


    }
}
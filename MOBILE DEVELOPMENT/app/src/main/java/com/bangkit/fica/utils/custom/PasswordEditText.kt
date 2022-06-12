package com.bangkit.fica.utils.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bangkit.fica.R


class PasswordEditText : AppCompatEditText, View.OnClickListener {

    private lateinit var passwordIcon : Drawable
    constructor(context: Context) : super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        showPasswordButton()
        textSize = 15f
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        hint = context.getString(R.string.hint_password)
    }


    private fun init(){
        passwordIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_visibility_off_24) as Drawable
        setOnClickListener(this)


        maxLines = 1
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, before: Int, count: Int, p3: Int)
            {
                if (s.toString().length<8)
                    showErrorPassword()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
    private fun showErrorPassword() {
        error = context.getString(R.string.failed_password)
    }

    private fun showPasswordButton() {
        setButtonDrawables(endOfTheText = passwordIcon)
    }

    private fun hideEyeButton() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    fun onTouch(event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val eyeButtonStart: Float
            val eyeButtonEnd: Float
            var isEyeButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                eyeButtonEnd = (passwordIcon.intrinsicWidth + paddingStart).toFloat()
                if (event.x < eyeButtonEnd) isEyeButtonClicked = true
            } else {
                eyeButtonStart = (width - paddingEnd - passwordIcon.intrinsicWidth).toFloat()
                if (event.x > eyeButtonStart) isEyeButtonClicked = true
            }

            if (isEyeButtonClicked) {
                return when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        hideEyeButton()
                        if (transformationMethod.equals(HideReturnsTransformationMethod.getInstance())) {
                            transformationMethod = PasswordTransformationMethod.getInstance() // hide password
                            passwordIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_visibility_off_24) as Drawable
                            showPasswordButton()
                        } else {
                            transformationMethod = HideReturnsTransformationMethod.getInstance() // show password
                            passwordIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_remove_red_eye_24) as Drawable
                            showPasswordButton()
                        }
                        true
                    }
                    else -> false
                }
            } else return false
        }
        return false
    }

    override fun onClick(p0: View?) {

    }
}
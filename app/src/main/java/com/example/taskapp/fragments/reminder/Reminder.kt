package com.example.taskapp.fragments.reminder

interface Reminder {
    fun setUpBinding()
    fun setUpDurationLayout()
    fun onDurationRadioChecked(id: Int)
    fun setupFrequencyLayout()
    fun onFrequencyRadioCheck(id: Int)

    /**
     * function responsible for changing visibility of buttons under RadioGroup depending
     * on current radio  checked
     */
    fun setDurationButtonsVisibility(id: Int)

    /**
     * function responsible for changing visibility of buttons under RadioGroup depending
     * on current radio  checked
     */
    fun setFrequencyButtonsVisibility(id: Int)
}
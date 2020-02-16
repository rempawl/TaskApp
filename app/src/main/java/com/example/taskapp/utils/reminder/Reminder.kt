package com.example.taskapp.utils.reminder


interface Reminder {
    fun setUpBinding()
    fun setUpDurationLayout()
    fun onDurationRadioChecked(id: Int)
    fun setupFrequencyLayout()
    fun onFrequencyRadioCheck(id: Int)
    fun showDurationDaysPickerDialog()
    fun showDaysOfWeekPickerDialog()
    fun showFrequencyPickerDialog()
    fun showEndDatePickerDialog()
    fun showBegDatePickerDialog()
    fun showNotificationPickerDialog()


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
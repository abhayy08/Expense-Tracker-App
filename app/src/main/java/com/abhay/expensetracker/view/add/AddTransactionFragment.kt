package com.abhay.expensetracker.view.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.abhay.expensetracker.R
import com.abhay.expensetracker.databinding.FragmentAddTransactionBinding
import com.abhay.expensetracker.model.Transaction
import com.abhay.expensetracker.util.Constants
import com.abhay.expensetracker.util.parseDouble
import com.abhay.expensetracker.util.snack
import com.abhay.expensetracker.util.transformIntoDatePicker
import com.abhay.expensetracker.view.base.BaseFragment
import com.abhay.expensetracker.view.main.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class AddTransactionFragment : BaseFragment<FragmentAddTransactionBinding, TransactionViewModel>() {
    override val viewModel: TransactionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val transactionTypeAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.item_autocomplete_layout,
                Constants.transactionType
            )
        val tagsAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_autocomplete_layout,
            Constants.transactionTags
        )

        with(binding) {

            addTransactionLayout.etTransactionType.setAdapter(transactionTypeAdapter)
            addTransactionLayout.etTag.setAdapter(tagsAdapter)

            // Transform TextInputEditText to DatePicker using Ext function
            addTransactionLayout.etWhen.transformIntoDatePicker(
                requireContext(),
                "dd/MM/yyyy",
                Date()
            )

            btnSaveTransaction.setOnClickListener {
                binding.addTransactionLayout.apply {
                    val (title, amount, transactionType, tag, date, note) = getTransactionContent()

                    // validate if transaction content is empty or not
                    when {
                        title.isEmpty() -> {
                            this.etTitle.error = "Title must not be empty"
                        }

                        amount.isNaN() -> {
                            this.etAmount.error = "Amount must not be empty"
                        }

                        transactionType.isEmpty() -> {
                            this.etTransactionType.error = "Transaction type must not be empty"
                        }

                        tag.isEmpty() -> {
                            this.etTag.error = "Tag must not be empty"
                        }

                        date.isEmpty() -> {
                            this.etWhen.error = "Date must not be empty"
                        }

                        note.isEmpty() -> {
                            this.etNote.error = "Note must not be empty"
                        }

                        else -> {
                            viewModel.insertTransaction(getTransactionContent()).run {
                                binding.root.snack(
                                    string = R.string.success_expense_saved
                                )
                                findNavController().navigate(
                                    R.id.action_addTransactionFragment_to_dashboardFragment
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    private fun getTransactionContent(): Transaction = binding.addTransactionLayout.let {
        val title = it.etTitle.text.toString()
        val amount = parseDouble(it.etAmount.text.toString())
        val transactionType = it.etTransactionType.text.toString()
        val tag = it.etTag.text.toString()
        val date = it.etWhen.text.toString()
        val note = it.etNote.text.toString()

        return Transaction(title, amount, transactionType, tag, date, note)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddTransactionBinding.inflate(inflater, container, false)

}
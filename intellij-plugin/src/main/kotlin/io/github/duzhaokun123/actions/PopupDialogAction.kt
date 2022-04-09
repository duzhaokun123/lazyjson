package io.github.duzhaokun123.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages

class PopupDialogAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val currentProject = e.project
        val message = StringBuilder(e.presentation.text + "Selected!")
        val selectedElement = e.getData(CommonDataKeys.NAVIGATABLE)
        if (selectedElement != null) {
            message.append("\nSelected element: $selectedElement")
        }
        val title = e.presentation.description ?: "null"
        Messages.showMessageDialog(currentProject, message.toString(), title, Messages.getInformationIcon())
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }
}
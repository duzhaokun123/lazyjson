package io.github.duzhaokun123.actions

import com.github.salomonbrys.kotson.fromJson
import com.intellij.lang.Language
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.file.PsiDirectoryFactory
import io.github.duzhaokun123.executeCouldRollBackAction
import io.github.duzhaokun123.lazyjson.Codegen
import io.github.duzhaokun123.showNotify
import io.github.duzhaokun123.ui.JsonInputDialog
import io.github.duzhaokun123.ui.prettyGson

class CodegenAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT) ?: return
        val dataContext = event.dataContext
        val module = LangDataKeys.MODULE.getData(dataContext) ?: return
        val directory = when (val navigatable = LangDataKeys.NAVIGATABLE.getData(dataContext)) {
            is PsiDirectory -> navigatable
            is PsiFile -> navigatable.containingDirectory
            else -> {
                val root = ModuleRootManager.getInstance(module)
                root.sourceRoots
                    .asSequence()
                    .mapNotNull {
                        PsiManager.getInstance(project).findDirectory(it)
                    }.firstOrNull()
            }
        } ?: return
        val directoryFactory = PsiDirectoryFactory.getInstance(directory.project)
        val packageName = directoryFactory.getQualifiedName(directory, false)
        val psiFileFactory = PsiFileFactory.getInstance(project)
        val packageDeclare = if (packageName.isNotEmpty()) "package $packageName" else ""
        val inputDialog = JsonInputDialog("", project)
        inputDialog.show()
        val className = inputDialog.getClassName()
        val inputString = inputDialog.inputString.takeIf { it.isNotEmpty() } ?: return
        runCatching { doGenerateKotlinDataClassFileAction(className, inputString, packageDeclare, project, psiFileFactory, directory) }
            .onFailure {
                Messages.showErrorDialog(project, it.stackTraceToString(), it.message ?: "unknown error")
            }
}

    private fun doGenerateKotlinDataClassFileAction(
        className: String,
        json: String,
        packageDeclare: String,
        project: Project?,
        psiFileFactory: PsiFileFactory,
        directory: PsiDirectory
    ) {
        val code = Codegen.generate(prettyGson.fromJson(json), packageDeclare.substringAfter("package"), className)
        executeCouldRollBackAction(project) {
            val file = psiFileFactory.createFileFromText("$className.kt", Language.findLanguageByID("kotlin") ?: Language.ANY, code)
            directory.add(file)
        }
        showNotify("generate success $className.kt", project)
    }
}
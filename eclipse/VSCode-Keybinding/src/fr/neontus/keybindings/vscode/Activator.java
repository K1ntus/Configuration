package fr.neontus.keybindings.vscode;

import org.eclipse.core.commands.Command;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {
    public static final String PLUGIN_ID = "fr.neontus.keybindings.vscode";
    private static Activator plugin;

    public Activator() {
    }

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        verifyCommandsAndContexts();
    }

    private void verifyCommandsAndContexts() {
        try {
            ICommandService commandService = PlatformUI.getWorkbench().getService(ICommandService.class);
            IContextService contextService = PlatformUI.getWorkbench().getService(IContextService.class);

            // File Operations
            verifyCommand(commandService, "org.eclipse.ui.file.save");
            verifyCommand(commandService, "org.eclipse.ui.file.saveAll");
            verifyCommand(commandService, "org.eclipse.ui.newWizard");
            verifyCommand(commandService, "org.eclipse.ui.file.closeAll");
            verifyCommand(commandService, "org.eclipse.ui.file.close");

            // Edit Operations
            verifyCommand(commandService, "org.eclipse.ui.edit.undo");
            verifyCommand(commandService, "org.eclipse.ui.edit.redo");
            verifyCommand(commandService, "org.eclipse.ui.edit.cut");
            verifyCommand(commandService, "org.eclipse.ui.edit.copy");
            verifyCommand(commandService, "org.eclipse.ui.edit.paste");
            verifyCommand(commandService, "org.eclipse.ui.edit.delete");
            verifyCommand(commandService, "org.eclipse.ui.edit.selectAll");

            // Search and Replace
            verifyCommand(commandService, "org.eclipse.ui.edit.findReplace");
            verifyCommand(commandService, "org.eclipse.ui.edit.findNext");
            verifyCommand(commandService, "org.eclipse.ui.edit.findPrevious");
            verifyCommand(commandService, "org.eclipse.search.ui.performTextSearchWorkspace");
            verifyCommand(commandService, "org.eclipse.jdt.ui.edit.text.java.search.references.in.workspace");

            // Navigation
            //verifyCommand(commandService, "org.eclipse.ui.navigate.goToLine");
            //verifyCommand(commandService, "org.eclipse.jdt.ui.edit.text.java.goto.matching.bracket");
            verifyCommand(commandService, "org.eclipse.ui.navigate.backwardHistory");
            verifyCommand(commandService, "org.eclipse.ui.navigate.forwardHistory");
            verifyCommand(commandService, "org.eclipse.ui.navigate.previous");
            verifyCommand(commandService, "org.eclipse.ui.navigate.next");

            // Editor Operations
            //verifyCommand(commandService, "org.eclipse.ui.edit.text.deleteLine");
            //verifyCommand(commandService, "org.eclipse.ui.edit.text.moveLineUp");
            //verifyCommand(commandService, "org.eclipse.ui.edit.text.moveLineDown");
            verifyCommand(commandService, "org.eclipse.ui.edit.text.upperCase");
            verifyCommand(commandService, "org.eclipse.ui.edit.text.lowerCase");
            verifyCommand(commandService, "org.eclipse.jdt.ui.edit.text.java.toggle.comment");
            verifyCommand(commandService, "org.eclipse.jdt.ui.edit.text.java.format");

            // Debug Operations
            verifyCommand(commandService, "org.eclipse.debug.ui.commands.ToggleBreakpoint");
            verifyCommand(commandService, "org.eclipse.debug.ui.commands.StepOver");
            verifyCommand(commandService, "org.eclipse.debug.ui.commands.StepInto");
            verifyCommand(commandService, "org.eclipse.debug.ui.commands.StepReturn");
            verifyCommand(commandService, "org.eclipse.debug.ui.commands.Resume");
            verifyCommand(commandService, "org.eclipse.debug.ui.commands.Terminate");

            // Code Navigation
            verifyCommand(commandService, "org.eclipse.jdt.ui.edit.text.java.open.editor");
            verifyCommand(commandService, "org.eclipse.jdt.ui.edit.text.java.show.outline");

            // Window Management
            verifyCommand(commandService, "org.eclipse.ui.window.maximizePart");
            verifyCommand(commandService, "org.eclipse.ui.window.previousEditor");
            verifyCommand(commandService, "org.eclipse.ui.window.nextEditor");
            verifyCommand(commandService, "org.eclipse.ui.window.quickAccess");
            verifyCommand(commandService, "org.eclipse.ui.views.showView");

            // Terminal
            verifyCommand(commandService, "org.eclipse.tm.terminal.view.ui.command.launchToolbar");

            // Contexts
            verifyContext(contextService, "org.eclipse.ui.contexts.window");
            verifyContext(contextService, "org.eclipse.ui.textEditorScope");
            verifyContext(contextService, "org.eclipse.jdt.ui.javaEditorScope");
            verifyContext(contextService, "org.eclipse.debug.ui.debugging");

        } catch (Exception e) {
            getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, "Error verifying commands and contexts", e));
        }
    }

    private void verifyCommand(ICommandService service, String commandId) {
        Command command = service.getCommand(commandId);
        if (!command.isDefined()) {
            getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, "Command not found: " + commandId));
        }
    }

    private void verifyContext(IContextService service, String contextId) {
        if (service.getContext(contextId) == null) {
            getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, "Context not found: " + contextId));
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    public static Activator getDefault() {
        return plugin;
    }
}
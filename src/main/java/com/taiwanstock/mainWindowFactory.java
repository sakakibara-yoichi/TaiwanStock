package com.taiwanstock;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.taiwanstock.components.EquoteComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class mainWindowFactory implements ToolWindowFactory, DumbAware {
    public static StatusBar statusBar; // 定義靜態的狀態欄物件

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        EquoteComponent equoteComponent = new EquoteComponent();
        statusBar = WindowManager.getInstance().getStatusBar(project);
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(equoteComponent.getContent(), "", true);
        content.getComponent().setMinimumSize(new Dimension(content.getComponent().getWidth(), 300));
        toolWindow.getContentManager().addContent(content);
    }
}

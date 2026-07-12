package com.craftermemorymods.screen;

import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class RecipeSearchWidget extends TextFieldWidget {
    private Runnable onTextChanged;
    
    public RecipeSearchWidget(int x, int y, int width, int height) {
        super(null, x, y, width, height, Text.literal("Search recipes..."));
        this.setPlaceholder(Text.literal("Search recipes..."));
    }
    
    public void setOnTextChanged(Runnable callback) {
        this.onTextChanged = callback;
    }
    
    @Override
    public void onChanged(String text) {
        super.onChanged(text);
        if (onTextChanged != null) {
            onTextChanged.run();
        }
    }
    
    public String getSearchText() {
        return this.getText();
    }
}

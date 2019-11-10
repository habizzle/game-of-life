package de.istkorrekt.gameoflife.presentation.terminal;

import com.googlecode.lanterna.input.KeyStroke;

enum Action {
    CURSOR_UP,
    CURSOR_RIGHT,
    CURSOR_DOWN,
    CURSOR_LEFT,
    RESET_CURSOR,
    SELECT,
    NEXT_ROUND,
    EXIT;

    static Action valueOfKeyStroke(KeyStroke keyStroke) {
        if (keyStroke != null) {
            switch (keyStroke.getKeyType()) {
                case ArrowRight:
                    return CURSOR_RIGHT;
                case ArrowUp:
                    return CURSOR_UP;
                case ArrowDown:
                    return CURSOR_DOWN;
                case ArrowLeft:
                    return CURSOR_LEFT;
                case Enter:
                    return NEXT_ROUND;
                case Escape:
                    return RESET_CURSOR;
                case Character:
                    if (keyStroke.getCharacter() == ' ') {
                        return SELECT;
                    }
                    if (keyStroke.isCtrlDown() && keyStroke.getCharacter() == 'c') {
                        return EXIT;
                    }
                    break;
            }
        }
        return null;
    }
}

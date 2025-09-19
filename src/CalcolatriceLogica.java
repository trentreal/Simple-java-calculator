public class CalcolatriceLogica {
    private StringBuilder display = new StringBuilder();

    public void aggiungiNumero(String n) {
        display.append(n);
    }

    public void aggiungiOperatore(String op) {
        display.append(op);
    }

    public void backspace() {
        if (display.length() > 0) {
            display.deleteCharAt(display.length() - 1);
        }
    }

    public void clear() {
        display.setLength(0);
    }

    public String calcola() {
        try {
            double result = eval(display.toString());
            display.setLength(0);
            display.append(result);
            return display.toString();
        } catch (Exception e) {
            display.setLength(0);
            return "Errore";
        }
    }

    public String getDisplay() {
        return display.toString();
    }

    private double eval(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException();
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                while (true) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                while (true) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException();
                }

                return x;
            }
        }.parse();
    }
}

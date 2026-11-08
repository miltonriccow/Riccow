package com.contaclara.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends Activity {
    private static final int INK = Color.rgb(36, 33, 61), PURPLE = Color.rgb(112, 88, 201), MUTED = Color.rgb(137, 135, 154);
    private SharedPreferences preferences;
    private LinearLayout root;
    private final Random random = new Random();
    private final String[] titles = {"Fundamentos da contabilidade", "Primeiros passos em finanças", "Desvendando os demonstrativos"};
    private final String[] categories = {"CONTABILIDADE", "FINANÇAS", "NA PRÁTICA"};
    private final String[] descriptions = {"Entenda a linguagem dos negócios e construa uma base sólida.", "Aprenda a tomar decisões financeiras com mais confiança.", "Descubra como os números contam a história de um negócio."};
    private final String[] lessons = {"A contabilidade registra e organiza informações sobre o patrimônio de uma entidade. O patrimônio é composto por bens e direitos (ativos) e obrigações (passivos). A diferença entre ativos e passivos é o patrimônio líquido.", "Um orçamento ajuda a planejar receitas e despesas. Quando as receitas são maiores que as despesas, existe um excedente que pode ser reservado para objetivos ou imprevistos.", "A demonstração de resultados apresenta receitas, despesas e o resultado de um período. Quando as receitas superam as despesas, o resultado é um lucro; no caso contrário, é um prejuízo."};
    private final String[] questions = {"Se uma empresa tem ativos de 100 e passivos de 60, qual é seu patrimônio líquido?", "Se você recebe 500 e gasta 400, qual é o excedente?", "Uma empresa teve receitas de 800 e despesas de 700. Qual foi o resultado?"};
    private final String[][] options = {{"40", "60", "160"}, {"100", "400", "900"}, {"Prejuízo de 100", "Lucro de 100", "Lucro de 700"}};
    private final int[] answers = {0, 0, 1};
    private final String[] explanations = {"Patrimônio líquido = ativos − passivos. Portanto, 100 − 60 = 40.", "O excedente é a diferença entre receitas e despesas: 500 − 400 = 100.", "Receitas menos despesas: 800 − 700 = 100. Como o resultado é positivo, há lucro."};

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        preferences = getSharedPreferences("progresso", MODE_PRIVATE);
        showHome();
    }
    private int dp(int n) { return (int) (n * getResources().getDisplayMetrics().density + .5f); }
    private GradientDrawable background(int color, int radius) { GradientDrawable d = new GradientDrawable(); d.setColor(color); d.setCornerRadius(dp(radius)); return d; }
    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this); t.setText(value); t.setTextSize(size); t.setTextColor(color); t.setLineSpacing(dp(3), 1f); if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD); return t;
    }
    private LinearLayout column() { LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); return l; }
    private void add(LinearLayout parent, View child, int top) { LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2); p.topMargin = dp(top); parent.addView(child, p); }
    private LinearLayout card(int color, int padding) { LinearLayout l = column(); l.setPadding(dp(padding), dp(padding), dp(padding), dp(padding)); l.setBackground(background(color, 18)); return l; }
    private Button button(String label, boolean filled) {
        Button b = new Button(this); b.setText(label); b.setTextSize(13); b.setAllCaps(false); b.setTypeface(Typeface.DEFAULT, Typeface.BOLD); b.setTextColor(filled ? Color.WHITE : PURPLE); b.setBackground(background(filled ? PURPLE : Color.rgb(244, 241, 252), 10)); return b;
    }
    private int count() { int n = 0; for (int i=0; i<titles.length; i++) if (preferences.getBoolean("lesson_"+i, false)) n++; return n; }
    private void showHome() {
        ScrollView scroll = new ScrollView(this); scroll.setFillViewport(true); scroll.setBackgroundColor(Color.rgb(248,248,251));
        root = column(); root.setPadding(dp(20), dp(26), dp(20), dp(28)); scroll.addView(root); setContentView(scroll);
        TextView logo = text("▥  contaclara", 22, PURPLE, true); add(root, logo, 0);
        add(root, text("SEU ESPAÇO DE APRENDIZAGEM", 10, MUTED, true), 28);
        add(root, text("Olá, estudante ✳", 25, INK, true), 5);
        add(root, text("Que bom ter você por aqui. Vamos aprender algo novo?", 13, MUTED, false), 5);
        LinearLayout hero = card(Color.rgb(238,234,250), 22); add(root, hero, 22);
        add(hero, text("✦  APRENDA NO SEU RITMO", 10, PURPLE, true), 0);
        add(hero, text("Seu futuro financeiro começa agora.", 25, INK, true), 12);
        add(hero, text("Descomplique a contabilidade e as finanças com pequenas lições e grandes descobertas.", 13, MUTED, false), 10);
        Button start = button("Começar a aprender  ↗", true); add(hero, start, 18); start.setOnClickListener(v -> openLesson(firstIncomplete()));
        LinearLayout stats = card(Color.WHITE, 18); add(root, stats, 16);
        add(stats, text("✦  " + (count()*10 + preferences.getInt("practice_points", 0)) + " pontos     ✓  " + count() + " lições     ◷  " + (count()*100/titles.length) + "%", 14, INK, true), 0);
        add(stats, text("Cada passo conta. Continue aprendendo!", 12, MUTED, false), 8);
        add(root, text("CONHECIMENTO QUE TRANSFORMA", 10, PURPLE, true), 30);
        add(root, text("Suas trilhas de aprendizado ✳", 20, INK, true), 7);
        add(root, text("Escolha por onde começar e evolua no seu tempo.", 12, MUTED, false), 5);
        for (int i=0; i<titles.length; i++) {
            final int index = i; boolean done = preferences.getBoolean("lesson_"+i, false);
            LinearLayout track = card(Color.WHITE, 20); add(root, track, 14);
            add(track, text("▤     " + categories[i] + "   ·   INICIANTE", 10, PURPLE, true), 0);
            add(track, text(titles[i], 17, INK, true), 13);
            add(track, text(descriptions[i], 12, MUTED, false), 7);
            add(track, text("◷  " + (i==2 ? "6" : "5") + " min     ▤  1 lição", 11, MUTED, false), 15);
            add(track, text(done ? "Concluída  ·  100%" : "Seu progresso  ·  0%", 11, PURPLE, true), 13);
            Button go = button(done ? "Revisar lição  ↗" : "Começar trilha  ↗", false); add(track, go, 13); go.setOnClickListener(v -> openLesson(index));
        }
        LinearLayout practice = card(Color.rgb(238, 234, 250), 20); add(root, practice, 18);
        add(practice, text("✦  PRÁTICA CONTÍNUA", 10, PURPLE, true), 0);
        add(practice, text("Sempre um novo desafio", 17, INK, true), 8);
        add(practice, text("Exercícios com valores variados sobre patrimônio, orçamento, resultados e juros simples. Ganhe 5 pontos por resposta correta.", 12, MUTED, false), 7);
        add(practice, text(preferences.getInt("practice_solved", 0) + " exercícios resolvidos", 11, PURPLE, true), 10);
        Button practiceButton = button("Praticar agora  ↗", true); add(practice, practiceButton, 13); practiceButton.setOnClickListener(v -> showPractice());
        LinearLayout challenge = card(Color.rgb(255,243,228), 20); add(root, challenge, 18);
        add(challenge, text("✦  COLOQUE EM PRÁTICA", 10, PURPLE, true), 0);
        add(challenge, text("Um pequeno desafio para hoje?", 16, INK, true), 8);
        add(challenge, text("Teste o que você sabe e ganhe pontos pelo caminho.", 12, MUTED, false), 6);
        Button challengeButton = button("Aceitar desafio  ↗", false); add(challenge, challengeButton, 14); challengeButton.setOnClickListener(v -> openLesson(firstIncomplete()));
        TextView footer = text("Feito para quem acredita no poder de aprender. ✦", 11, MUTED, false); footer.setGravity(Gravity.CENTER); add(root, footer, 28);
    }
    private int firstIncomplete() { for (int i=0; i<titles.length; i++) if (!preferences.getBoolean("lesson_"+i, false)) return i; return 0; }

    private void showPractice() {
        int kind = random.nextInt(4);
        int a, b, correct;
        String topic, question, explanation;
        if (kind == 0) {
            b = (random.nextInt(9) + 1) * 100;
            correct = (random.nextInt(9) + 1) * 100;
            a = b + correct;
            topic = "PATRIMÔNIO";
            question = "Uma empresa possui ativos de " + a + " e passivos de " + b + ". Qual é o patrimônio líquido?";
            explanation = "Patrimônio líquido = ativos − passivos: " + a + " − " + b + " = " + correct + ".";
        } else if (kind == 1) {
            b = (random.nextInt(9) + 1) * 100;
            correct = (random.nextInt(9) + 1) * 100;
            a = b + correct;
            topic = "ORÇAMENTO";
            question = "Uma pessoa recebe " + a + " e tem despesas de " + b + ". Qual é o excedente?";
            explanation = "Excedente = receitas − despesas: " + a + " − " + b + " = " + correct + ".";
        } else if (kind == 2) {
            b = (random.nextInt(9) + 1) * 100;
            correct = (random.nextInt(9) + 1) * 100;
            a = b + correct;
            topic = "RESULTADO";
            question = "Uma empresa teve receitas de " + a + " e despesas de " + b + ". Qual é o lucro?";
            explanation = "Lucro = receitas − despesas: " + a + " − " + b + " = " + correct + ".";
        } else {
            a = (random.nextInt(9) + 1) * 100;
            b = random.nextInt(5) + 1;
            int months = random.nextInt(6) + 1;
            correct = a * b * months / 100;
            topic = "JUROS SIMPLES";
            question = "Um capital de " + a + " rende juros simples de " + b + "% ao mês por " + months + " meses. Qual é o valor dos juros?";
            explanation = "Juros = capital × taxa × tempo: " + a + " × " + b + "% × " + months + " = " + correct + ".";
        }
        final int result = correct;
        LinearLayout content = card(Color.WHITE, 4);
        add(content, text("PRÁTICA CONTÍNUA  ·  " + topic, 10, PURPLE, true), 0);
        add(content, text(question, 17, INK, true), 12);
        TextView feedback = text("", 13, MUTED, false);
        int[] choices = {correct, correct + (kind == 3 ? 10 : 100), Math.max(0, correct - (kind == 3 ? 10 : 100))};
        // Shuffle the options without changing the correct answer.
        for (int i = choices.length - 1; i > 0; i--) { int j = random.nextInt(i + 1); int temp = choices[i]; choices[i] = choices[j]; choices[j] = temp; }
        AlertDialog dialog = new AlertDialog.Builder(this).setNegativeButton("Sair", (d,w) -> showHome()).create();
        for (int value : choices) {
            Button answer = button(String.valueOf(value), false); add(content, answer, 8);
            answer.setOnClickListener(v -> {
                if (value == result) {
                    preferences.edit().putInt("practice_points", preferences.getInt("practice_points", 0) + 5).putInt("practice_solved", preferences.getInt("practice_solved", 0) + 1).apply();
                    feedback.setTextColor(Color.rgb(66,143,100)); feedback.setText("Correto! +5 pontos. " + explanation);
                    for (int k = 0; k < content.getChildCount(); k++) if (content.getChildAt(k) instanceof Button) content.getChildAt(k).setEnabled(false);
                    Button next = button("Próximo exercício  ↗", true); add(content, next, 12); next.setOnClickListener(view -> { dialog.dismiss(); showPractice(); });
                } else { feedback.setTextColor(Color.rgb(185,100,100)); feedback.setText("Ainda não. Revise os valores e tente novamente."); }
            });
        }
        add(content, feedback, 12);
        ScrollView scroll = new ScrollView(this); int pad = dp(24); scroll.setPadding(pad,pad,pad,0); scroll.addView(content); dialog.setView(scroll);
        dialog.setOnCancelListener(d -> showHome()); dialog.show();
    }
    private void openLesson(int index) {
        LinearLayout content = card(Color.WHITE, 4);
        add(content, text(categories[index] + "  ·  LIÇÃO " + (index+1), 10, PURPLE, true), 0);
        add(content, text(titles[index], 22, INK, true), 10);
        add(content, text(lessons[index], 14, MUTED, false), 12);
        add(content, text("HORA DE PRATICAR  ✦  +10 PONTOS", 10, PURPLE, true), 22);
        add(content, text(questions[index], 15, INK, true), 10);
        TextView feedback = text("", 13, MUTED, false);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(content).setNegativeButton("Fechar", (d,w) -> showHome()).create();
        for (int j=0; j<options[index].length; j++) {
            final int choice = j; Button answer = button(options[index][j], false); add(content, answer, 8);
            answer.setOnClickListener(v -> {
                if (choice == answers[index]) {
                    preferences.edit().putBoolean("lesson_"+index, true).apply();
                    feedback.setTextColor(Color.rgb(66,143,100)); feedback.setText("Muito bem! " + explanations[index]);
                    for (int k=0; k<content.getChildCount(); k++) if (content.getChildAt(k) instanceof Button) content.getChildAt(k).setEnabled(false);
                } else { feedback.setTextColor(Color.rgb(185,100,100)); feedback.setText("Ainda não. Tente novamente!"); }
            });
        }
        add(content, feedback, 12);
        ScrollView scroll = new ScrollView(this); int pad = dp(24); scroll.setPadding(pad,pad,pad,0); scroll.addView(content); dialog.setView(scroll);
        dialog.setOnCancelListener(d -> showHome()); dialog.show();
    }
}

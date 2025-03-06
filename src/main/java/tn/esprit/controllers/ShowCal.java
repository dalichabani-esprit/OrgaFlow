package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShowCal implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private GridPane calendar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(dateFocus.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));

        LocalDate localDateFocus = dateFocus.toLocalDate();
        YearMonth yearMonth = YearMonth.from(localDateFocus);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        DayOfWeek dayOfWeek = firstOfMonth.getDayOfWeek();
        int daysInMonth = yearMonth.lengthOfMonth();

        int dayOffset = dayOfWeek.getValue() % 7; // Adjust for Monday being 1, Sunday being 0.

        // Add day headers
        String[] dayNames = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
        for (int i = 0; i < 7; i++) {
            Text dayNameText = new Text(dayNames[i]);
            calendar.add(dayNameText, i, 0); // Add day headers to the first row
        }

        int row = 1; // Start from the second row for dates
        int col = dayOffset;
        for (int i = 1; i <= daysInMonth; i++) {
            LocalDate date = LocalDate.of(localDateFocus.getYear(), localDateFocus.getMonth(), i);
            StackPane dayPane = new StackPane();
            Rectangle rectangle = new Rectangle(50, 50); // Adjust size as needed
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Color.LIGHTGRAY);
            dayPane.getChildren().add(rectangle);

            Text dayText = new Text(String.valueOf(i));
            dayPane.getChildren().add(dayText);

            if (date.equals(today.toLocalDate())) {
                rectangle.setFill(Color.LIGHTBLUE); // Highlight today
            }

            calendar.add(dayPane, col, row);
            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }
}
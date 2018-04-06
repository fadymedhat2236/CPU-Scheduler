package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;


import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Controller implements Initializable
{
    @FXML private TableView<process> table1;
    @FXML private TableColumn<process,String> nameColumn;
    @FXML private TableColumn<process,Double> arrivalTimeColumn;
    @FXML private TableColumn<process,Double> burstTimeColumn;
    @FXML private TableColumn<process,Integer> priorityColumn;

    @FXML private Button addButton;
    @FXML private Button deletebutton;
    @FXML private Button scheduleButton;

    @FXML private TextField nameTextField;
    @FXML private TextField arrivalTimeTextField;
    @FXML private TextField burstTimeTextField;
    @FXML private TextField priorityTextField;

    @FXML private ComboBox c1;
    @FXML private RadioButton preempitive;
    @FXML private RadioButton nonpreempitive;
    private ToggleGroup group1;

    @FXML private TextField quantum;

    public void initialize(URL url, ResourceBundle rb)
    {
        //for the combobox
        c1.getItems().addAll(
                "FIFO","SJF","priority","round robin"
        );

        //for the radiobuttons
        group1=new ToggleGroup();
        this.preempitive.setToggleGroup(group1);
        this.nonpreempitive.setToggleGroup(group1);

        preempitive.setVisible(false);
        nonpreempitive.setVisible(false);
        quantum.setVisible(false);
        priorityColumn.setVisible(false);
        priorityTextField.setVisible(false);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        burstTimeColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        table1.setItems(getProduct());


        table1.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        arrivalTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        burstTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priorityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }

    public void changeNameCellEvent(TableColumn.CellEditEvent edittedCell)
    {
        process processSelected =  table1.getSelectionModel().getSelectedItem();
        processSelected.setName(edittedCell.getNewValue().toString());
    }
    public void changeArrivalTimeCellEvent(TableColumn.CellEditEvent edittedCell)
    {
        process processSelected =  table1.getSelectionModel().getSelectedItem();
        processSelected.setArrivalTime(Double.parseDouble(edittedCell.getNewValue().toString()));
    }
    public void changeBurstTimeCellEvent(TableColumn.CellEditEvent edittedCell)
    {
        process processSelected =  table1.getSelectionModel().getSelectedItem();
        processSelected.setBurstTime(Double.parseDouble(edittedCell.getNewValue().toString()));
    }

    public void changePriorityCellEvent(TableColumn.CellEditEvent edittedCell)
    {
        process processSelected =  table1.getSelectionModel().getSelectedItem();
        processSelected.setPriority(Integer.parseInt(edittedCell.getNewValue().toString()));
    }

    private ObservableList<process> getProduct()
    {
        ObservableList<process> products = FXCollections.observableArrayList();

        products.add(new process("p1",0,10,4));
        products.add(new process("p2",2,5,3));
        products.add(new process("p3",4,3,5));
        products.add(new process("p4",6,2,1));
        products.add(new process("p5",7,1,2));

        return products;
    }


    //Add button clicked
    public void addButtonClicked()
    {
        process p = new process();
        if(nameTextField.getText().isEmpty() ||
                arrivalTimeTextField.getText().isEmpty() ||
                burstTimeTextField.getText().isEmpty() ||
                (priorityTextField.getText().isEmpty() && c1.getValue()=="priority")
                )
        {
            alertBox alertBox1=new alertBox();
            alertBox1.display("Error","Missing Data!!!");
            return;
        }
        try
        {
            p.setName(nameTextField.getText());
            p.setArrivalTime(Double.parseDouble(arrivalTimeTextField.getText()));
            p.setBurstTime(Double.parseDouble(burstTimeTextField.getText()));
            if (c1.getValue() == "priority")
                p.setPriority(Integer.parseInt(priorityTextField.getText()));
            table1.getItems().add(p);
            nameTextField.clear();
            arrivalTimeTextField.clear();
            burstTimeTextField.clear();
            priorityTextField.clear();
        }
        catch(NumberFormatException x)
        {
            alertBox alertBox1=new alertBox();
            alertBox1.display("Error","Please enter numbers not strings!!!");
            return;
        }
    }

    //Delete button clicked
    public void deleteButtonClicked()
    {
        ObservableList<process> selected, allProcesses;
        allProcesses = table1.getItems();
        selected = table1.getSelectionModel().getSelectedItems();

        for(process p:selected)
            allProcesses.remove(p);
    }

    //function for combobox
    public void comboBoxFunction()
    {
        if(c1.getValue()=="priority")
        {
            priorityColumn.setVisible(true);
            priorityTextField.setVisible(true);
        }
        else
        {
            priorityColumn.setVisible(false);
            priorityTextField.setVisible(false);
        }
        if(c1.getValue()=="priority" || c1.getValue()=="SJF")
        {
            preempitive.setVisible(true);
            nonpreempitive.setVisible(true);
            nonpreempitive.setSelected(true);
        }
        else
        {
            preempitive.setVisible(false);
            nonpreempitive.setVisible(false);
        }
        if(c1.getValue()=="round robin")
            quantum.setVisible(true);
        else
            quantum.setVisible(false);
    }

    public void scheduleButtonPressed()
    {
        List<process> l1 = table1.getItems().stream().collect(Collectors.toList());
        for(process p:l1)
        {
            if(p.getArrivalTime()<0 || p.getBurstTime()<0 || p.getPriority()<0)
            {
                alertBox alertBox1=new alertBox();
                alertBox1.display("Error","Please change the negative numbers!!!");
                return;
            }
        }

        scheduler s1=new scheduler(l1);

        List<node> nodes=new ArrayList<node>() ;

        String str=new String();

        if(c1.getValue()==null)
        {
            alertBox alertBox1=new alertBox();
            alertBox1.display("Error","You must choose an algorithm");
        }
        else
        {
            if(c1.getValue()=="FIFO")
            {
                nodes = s1.FIFOSort();
                str="FIFO";
            }
            else if(c1.getValue()=="SJF")
            {
                if(nonpreempitive.isSelected())
                {
                    nodes=s1.SJFNonpreempitive();
                    str="SJF Nonpreempitive";
                }
                else if(preempitive.isSelected())
                {
                    nodes=s1.SJFPreempitive();
                    str="SJF preempitive";
                }
            }
            else if(c1.getValue()=="priority")
            {
                if(nonpreempitive.isSelected())
                {
                    nodes=s1.priorityNonpreempitive();
                    str="Priority Nonpreempitive";
                }
                else if(preempitive.isSelected())
                {
                    nodes=s1.priorityPreempitive();
                    str="Priority preempitive";
                }
            }
            else
            {
                str="Round robin";
                double q;
                try
                {
                    q = Double.parseDouble(quantum.getText());
                }
                catch(NumberFormatException x)
                {
                    alertBox alertBox1=new alertBox();
                    alertBox1.display("Error","Please enter a valid number for the quantum!!!");
                    return;
                }
                quantum.clear();
                nodes=s1.roundRobin(q);
            }

            Stage chart = new Stage();
            str+=" Gantt chart";
            chart.setTitle(str);
            chart.setMinWidth(1200);
            chart.setMinHeight(300);
            chart.setResizable(false);
            VBox parent=new VBox();
            HBox layout1=new HBox();
            Scene scene = new Scene(parent);
            parent.setAlignment(Pos.CENTER);
            double totalTime=nodes.get(nodes.size()-1).getEndTime();

            for(int i=0;i<nodes.size();i++)
            {
                double length=nodes.get(i).getEndTime()-nodes.get(i).getBeginTime();
                Label l=new Label(nodes.get(i).getName());
                l.setPrefSize(length*1200/totalTime,50);
                l.setStyle("-fx-border-color: black");
                layout1.getChildren().add(l);
            }
            HBox layout2=new HBox();
            for(int i=0;i<nodes.size();i++)
            {
                double length=nodes.get(i).getEndTime()-nodes.get(i).getBeginTime();
                Label l=new Label(Double.toString(nodes.get(i).getBeginTime()));
                l.setPrefSize(length*1200/totalTime,20);
                layout2.getChildren().add(l);
            }
            layout2.getChildren().add(new Label(Double.toString(nodes.get(nodes.size()-1).getEndTime())));
            parent.getChildren().addAll(layout1,layout2);
            HBox waitingTimeHbox=new HBox();
            waitingTimeHbox.getChildren().add(new Label("Average waiting time="+Double.toString(s1.geAveragetWaitingTime())));
            HBox turnaroundTimeHbox=new HBox();
            turnaroundTimeHbox.getChildren().add(new Label("Average turnaround time="+Double.toString(s1.geAveragetTurnAroundTime())));
            parent.getChildren().addAll(waitingTimeHbox,turnaroundTimeHbox);
            chart.setScene(scene);
            chart.show();
        }






    }

}

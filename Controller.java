package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable
{
    @FXML private TableView<process> table1;
    @FXML private TableColumn<process,String> nameColumn;
    @FXML private TableColumn<process,Integer> arrivalTimeColumn;
    @FXML private TableColumn<process,Integer> burstTimeColumn;
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
    }


    private ObservableList<process> getProduct()
    {
        ObservableList<process> products = FXCollections.observableArrayList();
        products.add(new process("p1",0,10,4));
        products.add(new process("p2",2,5,3));
        products.add(new process("p3",4,3,5));
        products.add(new process("p4",6,2,1));
        products.add(new process("p5",7,1,2));


        /*
        products.add(new process("p1",20,1,5));
        products.add(new process("p2",23,10,3));
        products.add(new process("p3",24,6,1));
        products.add(new process("p4",25,20,2));
        products.add(new process("p5",26,4,1));
        */
        /*
        products.add(new process("p1",0,10,3));
        products.add(new process("p2",1,1,1));
        products.add(new process("p3",2,2,4));
        products.add(new process("p4",3,1,5));
        products.add(new process("p5",4,5,2));
*/

        return products;
    }


    //Add button clicked
    public void addButtonClicked()
    {
        process p = new process();
        p.setName(nameTextField.getText());
        p.setArrivalTime(Double.parseDouble(arrivalTimeTextField.getText()));
        p.setBurstTime(Double.parseDouble(burstTimeTextField.getText()));
        if(c1.getValue()=="priority")
            p.setPriority(Integer.parseInt(priorityTextField.getText()));
        table1.getItems().add(p);
        nameTextField.clear();
        arrivalTimeTextField.clear();
        burstTimeTextField.clear();
        priorityTextField.clear();

    }

    //Delete button clicked
    public void deleteButtonClicked(){
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

        scheduler s1=new scheduler(l1);

        List<node> nodes;

        System.out.println(c1.getValue());
        if(c1.getValue()==null)
        {
            alertBox alertBox1=new alertBox();
            alertBox1.display("Error","You must choose an algorithm");
        }
        else
        {
            if(c1.getValue()=="FIFO")
            {
                nodes=s1.FIFOSort();
                for(node x: nodes)
                    System.out.println(x);
                System.out.println(".............");
                System.out.println(s1.geAveragetWaitingTime());
                System.out.println(s1.geAveragetTurnAroundTime());
                System.out.println(".............");
            }
            else if(c1.getValue()=="SJF")
            {
                if(nonpreempitive.isSelected())
                {
                    nodes=s1.SJFNonpreempitive();
                    for(node x: nodes)
                        System.out.println(x);
                    System.out.println(".............");
                    System.out.println(s1.geAveragetWaitingTime());
                    System.out.println(s1.geAveragetTurnAroundTime());
                    System.out.println(".............");
                }
                else if(preempitive.isSelected())
                {
                    nodes=s1.SJFPreempitive();
                    for(node x: nodes)
                        System.out.println(x);
                    System.out.println(".............");
                    System.out.println(s1.geAveragetWaitingTime());
                    System.out.println(s1.geAveragetTurnAroundTime());
                    System.out.println(".............");
                }
            }
            else if(c1.getValue()=="priority")
            {
                if(nonpreempitive.isSelected())
                {
                    nodes=s1.priorityNonpreempitive();
                    for(node x: nodes)
                        System.out.println(x);
                    System.out.println(".............");
                    System.out.println(s1.geAveragetWaitingTime());
                    System.out.println(s1.geAveragetTurnAroundTime());
                    System.out.println(".............");
                }
                else if(preempitive.isSelected())
                {
                    nodes=s1.priorityPreempitive();
                    for(node x: nodes)
                        System.out.println(x);
                    System.out.println(".............");
                    System.out.println(s1.geAveragetWaitingTime());
                    System.out.println(s1.geAveragetTurnAroundTime());
                    System.out.println(".............");
                }
            }
            else
            {
                double q=Double.parseDouble(quantum.getText());
                nodes=s1.roundRobin(q);
                for(node x: nodes)
                    System.out.println(x);
                System.out.println(".............");
                System.out.println(s1.geAveragetWaitingTime());
                System.out.println(s1.geAveragetTurnAroundTime());
                System.out.println(".............");
            }

        }






    }

}

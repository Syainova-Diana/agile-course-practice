package ru.unn.agile.TemperatureConverter.view;

import ru.unn.agile.TemperatureConverter.viewmodel.ViewModel;
import ru.unn.agile.TemperatureConverter.viewmodel.ViewModel.Scale;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public final class Converter {
    private JPanel mainJPanel;
    private JTextField txtValue;
    private JTextField txtResult;
    private JButton btnConvert;
    private JTextField txtStatus;
    private JComboBox<Scale> cbScale;
    private ViewModel viewModel;

    public static void main(final String[] args) {
        JFrame frame = new JFrame("Converter");
        frame.setContentPane(new Converter(new ViewModel()).mainJPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private Converter() { }

    private Converter(final ViewModel viewModel) {
        this.viewModel = viewModel;
        loadListOfScales();
        backBind();

        btnConvert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                viewModel.convert();
                backBind();
            }
        });

        cbScale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                backBind();
            }
        });

        KeyAdapter keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent keyEvent) {
                bind();
                viewModel.parseInput();
                backBind();
            }
        };
        txtValue.addKeyListener(keyListener);
    }

    private void loadListOfScales() {
        Scale[] scales = ViewModel.Scale.values();
        cbScale.setModel(new JComboBox<>(scales).getModel());
    }

    private void bind() {
        viewModel.setInputValue(txtValue.getText());
        viewModel.setScale((Scale) cbScale.getSelectedItem());
    }

    private void backBind() {
        cbScale.setSelectedItem(viewModel.getScale());
        btnConvert.setEnabled(viewModel.isConvertButtonEnabled());
        txtResult.setText(viewModel.getResult());
        txtStatus.setText(viewModel.getStatus());
    }
}

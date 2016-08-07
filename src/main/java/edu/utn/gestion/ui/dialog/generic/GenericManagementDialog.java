package edu.utn.gestion.ui.dialog.generic;

import edu.utn.gestion.exception.GestionAppException;
import edu.utn.gestion.ui.constants.UIConstants;
import edu.utn.gestion.ui.controller.generic.GenericController;
import edu.utn.gestion.ui.util.IconFactory;
import edu.utn.gestion.ui.util.PopUpFactory;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;

import org.apache.commons.collections4.CollectionUtils;

/**
 *
 * @author Gerardo Martín Roldán
 */
public abstract class GenericManagementDialog<E, I> extends JDialog {
    protected final GenericTableModel model;
    protected JButton btnDelete;
    protected JButton btnExport;
    protected JButton btnNew;
    protected JButton btnSearch;
    protected JScrollPane jScrollPane1;
    protected JPanel searchPanel;
    protected JTable tableBooks;
    protected JTextField txtSearch;
    protected JCheckBox checkBoxShowDeletedObjects;

    /**
     * Creates new form BookManagerDialog
     * 
     * @param parent
     * @param windowTitle
     * @param modal
     * @parm model
     */
    public GenericManagementDialog(Frame parent, String windowTitle
            , boolean modal, GenericTableModel model) {
        super(parent, windowTitle, modal);
        this.model = model;
        this.initComponents();
        this.setLocationRelativeTo(parent);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    protected void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.tableBooks = new JTable();
        this.btnNew = new JButton("New", IconFactory.getIcon(UIConstants.ICON_EDIT_ADD_LOCATION));
        this.btnDelete = new JButton("Delete", IconFactory.getIcon(UIConstants.ICON_EDIT_DELETE_LOCATION));
        this.btnExport = new JButton("Export", IconFactory.getIcon(UIConstants.ICON_DOCUMENT_EXPORT_LOCATION));
        this.btnSearch = new JButton("Search", IconFactory.getIcon(UIConstants.ICON_DOCUMENT_SEARCH_LOCATION));
        this.searchPanel = new JPanel();
        this.txtSearch = new JTextField();
        this.checkBoxShowDeletedObjects = new JCheckBox("Show deleted objects");
        this.checkBoxShowDeletedObjects.setVisible(false); // Ii's disabled by default.

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        this.addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(WindowEvent evt) {
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        this.tableBooks.setModel(this.model);
        this.tableBooks.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tableBooksMouseClicked(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.tableBooks);

        this.btnNew.addActionListener(event -> this.btnNewActionPerformed());
        this.btnDelete.addActionListener(event -> this.btnDeleteActionPerformed());
        this.btnExport.addActionListener(event -> this.btnExportActionPerformed());
        this.btnSearch.addActionListener(event -> this.btnSearchActionPerformed());
        this.checkBoxShowDeletedObjects.addItemListener(event -> this.checkBoxChanged(event));

        this.searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));

        this.txtSearch.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        GroupLayout searchPanelLayout = new GroupLayout(this.searchPanel);
        this.searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this.txtSearch)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this.btnSearch)
                .addContainerGap())
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(this.txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(this.btnSearch))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(this.jScrollPane1, GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(this.btnNew)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(this.btnDelete)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(this.btnExport)
                        .addComponent(this.checkBoxShowDeletedObjects)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(this.searchPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {this.btnDelete, this.btnNew});

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this.searchPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(this.btnDelete)
                    .addComponent(this.btnNew)
                    .addComponent(this.btnExport)
                    .addComponent(this.checkBoxShowDeletedObjects))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this.jScrollPane1, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    protected abstract void checkBoxChanged(ItemEvent event);

    private void btnExportActionPerformed() {
        try {
            this.getController().exportData(this.model);
        } catch (GestionAppException ex) {
            PopUpFactory.showErrorMessage(this, ex.getMessage());
        }
    }

    private void formWindowOpened(WindowEvent event) {
        this.updateObjectList();
    }

    private void tableBooksMouseClicked(MouseEvent event) {
        int rowIndex = this.tableBooks.getSelectedRow();
        int columnIndex = this.tableBooks.getSelectedColumn();
        
        if (rowIndex >= 0) {
            if (columnIndex == 0) {
                this.editSelectedObject(rowIndex, columnIndex);
            } else {
                this.refreshSelectedObjects();
            }
        }
    }

    protected void btnNewActionPerformed() {
        this.showNewObjectDialog();
    }

    private void formWindowGainedFocus(WindowEvent event) {
        this.updateObjectList();
    }

    private void btnDeleteActionPerformed() {
        this.deleteSelectedObjects();        
        this.updateObjectList();
    }

    private void btnSearchActionPerformed() {
        this.search();
    }

    private void txtSearchKeyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            this.search();
        }
    }

    protected void updateObjectList() {
        try {
             this.model.setObjectList(this.getController().findAll());
        } catch (GestionAppException ex) {
            PopUpFactory.showErrorMessage(this, ex.getMessage());
            this.dispose();
        }
    }
    
    private void editSelectedObject(int rowIndex, int columnIndex) {
        I id = (I) this.model.getValueAt(rowIndex, columnIndex);

        try {
            E object = this.getController().findOne(id);
            this.showEditObjectDialog(object);            
        } catch (GestionAppException ex) {
            PopUpFactory.showErrorMessage(this, ex.getMessage());
        }
    }
    
    private void refreshSelectedObjects() {
        int[] selectedRows = this.tableBooks.getSelectedRows();
        this.model.refreshSelectedObjects(selectedRows);
    }
    
    // TODO: this method should be optimized.
    private void deleteSelectedObjects() {
        Set<E> selectedBooks = this.model.getSelectedObjects();
        
        for (E object : selectedBooks) {
            try {
                this.getController().delete(object);
            } catch (GestionAppException ex) {
                PopUpFactory.showErrorMessage(this, ex.getMessage());
            }
        }
    }
    
    private void search() {
        List<E> searchResult = null;
        
        try {
            searchResult = this.getController().findBySearch(this.txtSearch.getText());
        } catch (GestionAppException ex) {
            PopUpFactory.showErrorMessage(this, ex.getMessage());
        }
        
        if (CollectionUtils.isNotEmpty(searchResult)) {
            this.model.setObjectList(searchResult);
        }
    }
    
    protected abstract void showEditObjectDialog(E object);
    protected abstract void showNewObjectDialog();
    protected abstract GenericController<E, I> getController();
}

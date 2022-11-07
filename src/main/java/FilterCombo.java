import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FilterCombo extends JComboBox {

   public FilterCombo(String[] itemList) {
      FilterComboBoxModel model = new FilterComboBoxModel(itemList);
      setModel(model);

      model.addAllElements(itemList);

      // Remove standard key listeners that come with the JComboBox
      KeyListener[] lis = getKeyListeners();
       for (KeyListener li : lis) {
           removeKeyListener(li);
       }

      // Add custom KeyListener class
      addKeyListener(model.getKeyListener());

      // Add custom ActionListener class
      addActionListener(model.getActionListener());

      // Add your own custom action listener here if needed
      addActionListener((ActionEvent e) -> {
          System.out.println("Action Listener in FilterCombo class -- Add your code here");
      });

      // Add your own custom item listener here if needed
      addItemListener((ItemEvent e) -> {
          System.out.println("Item Listener in FilterCombo class -- Add your code here");
      });

   }

   public class FilterComboBoxModel extends DefaultComboBoxModel {

      private String[] masterItemList;
      private String masterSelectedItem;
      private final StringBuilder filter = new StringBuilder(8);
      private int fidx = 0;
      private CustomKeyListener keylis = null;
      private CustomActionListener actionlis = null;

      private ActionListener[] actionLisList;
      private ItemListener[] itemLisList;

      public FilterComboBoxModel(String[] itemList) {
         initComboBoxModel(itemList);
      }

      private void initComboBoxModel( String[] itemList ) {
         setMasterItemList(itemList);
      }

      public CustomKeyListener getKeyListener() {
         if (keylis == null) {
            keylis = new CustomKeyListener();
         }
         return keylis;
      }

      public CustomActionListener getActionListener() {
         if (actionlis == null) {
            actionlis = new CustomActionListener();
         }
         return actionlis;
      }

      public void setMasterItemList(String[] items) {
         masterItemList = items;
         masterSelectedItem = items[0];
         restoreItems();
      }

      public void addAllElements(Object[] items) {
          for (Object item : items) {
              addItem(item);
          }
      }

      public Object[] getAllElements() {
         Object[] list = new Object[getItemCount()];
         for (int i = 0; i < list.length; i++) {
            list[i] = this.getElementAt(i);
         }
         return list;
      }

      public void filterItems(String pat) {

         ArrayList<String> newList = new ArrayList<>();

         String[] list = masterItemList;

         int patlen = pat.length();
          for (String item : list) {
              if (item.length() < patlen) {
                  continue;
              }
              String tok = item.substring(0, patlen);
              
              if (tok.equalsIgnoreCase(pat)) {
                  newList.add(item);
              }
          }

         // Add the new list to the combobox - notice we disable listeners
         suspendAllListeners();
         removeAllElements();
         if (newList.isEmpty()) {
            addItem("<Empty>");
         } else {
            addAllElements(newList.toArray());
         }
         if (fidx == 0)
            setSelectedItem(masterSelectedItem);

         restoreAllListeners();

      }

      private void suspendAllListeners() {
         actionLisList = getActionListeners();
         for (ActionListener a : actionLisList) {
            removeActionListener(a);
         }
         itemLisList = getItemListeners();
         for (ItemListener i : itemLisList) {
            removeItemListener(i);
         }
      }

      private void restoreAllListeners() {
         for (ActionListener a : actionLisList) {
            addActionListener(a);
         }
         for (ItemListener i : itemLisList) {
            addItemListener(i);
         }
      }

      public void restoreItems() {
         suspendAllListeners();
         removeAllElements();
         addAllElements(masterItemList);
         resetFilter();
         setSelectedItem(masterSelectedItem);
         restoreAllListeners();
      }

      private void resetFilter() {
         filter.setLength(0);
         fidx = 0;
      }

      public class CustomKeyListener extends KeyAdapter {

         @Override
         public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) {
               char letter = (char) keyCode;
               filter.insert(fidx, letter);
               fidx++;
               filter.setLength(fidx);
               filterItems(filter.toString());
            } else if (keyCode == KeyEvent.VK_LEFT) {
               fidx = (fidx == 0 ? fidx : fidx - 1);
               filterItems(filter.substring(0, fidx));
            } else if (keyCode == KeyEvent.VK_RIGHT) {
               fidx = (fidx == filter.length() ? fidx : fidx + 1);
               filterItems(filter.substring(0, fidx));
            } else if (keyCode == KeyEvent.VK_BACK_SPACE
                    || keyCode == KeyEvent.VK_DELETE
                    || keyCode == KeyEvent.VK_ESCAPE) {
               restoreItems();
            }

//            System.out.println("fidx = " + fidx + " -- Filter: " + filter);
            setToolTipText("Current filter: " + filter.substring(0, fidx));
         }
      }

      public class CustomActionListener implements ActionListener {
         @Override
         public void actionPerformed(ActionEvent e) {
            masterSelectedItem = (String) getSelectedItem();
            restoreItems();
         }
      }
      
   }

   public static void main(String[] args) {
      

      FilterCombo fc = new FilterCombo(new String[]{
                 "apple",
                 "banana",
                 "citrus",
                 "Apple",
                 "Ball",
                 "Cat",
                 "Dog",
                 "Egg",
                 "Fish",
                 "aaaaaaaaaaaaaaaaaa",
                 "bbbbbbbbbbbbbbbbbb",
                 "cccccccccccccccccc",
                 "ababababababababab",
                 "bababababababababa",
                 "cacaacacacacacacac",
              });

      // Create the frame
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLocationRelativeTo(null);
      frame.setTitle("ComboBox Demo");
     
      


      JPanel panel = new JPanel();
      frame.setBounds(100, 200, 500, 500);
      panel.add(fc);
      panel.setBackground(Color.yellow);
      frame.add(panel);
//      frame.pack();

      frame.setVisible(true);
      
   }
   
   
   
}

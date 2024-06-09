package Main;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client 
{
    private JFrame ArayuzFrame;
    private JPanel TopBarPanel;
    private JPanel KapatPanel;
    private JLabel KapatLabel;
    private JPanel MinimizePanel;
    private JLabel MinimizeLabel;
    private JPanel BaslikPanel;
    private JLabel BaslikLabel;
    private JLabel IconLabelBox;
    private JFrame KullaniciGirisFrame;
    private JPanel GirisTopBarPanel;
    private JPanel GirisKapatPanel;
    private JLabel GirisKapatLabel;
    private JPanel GirisBaslikPanel;
    private JLabel GirisBaslikLabel;
    private JLabel GirisIconLabelBox;
    private JTextField KullaniciAdiTextBox;
    private JPanel GirisYapPanel;
    private JLabel GirisYapLabel;
    private JLabel KullaniciAdiGirLabel;
    private JLabel HosgeldinizLabel;
    private JLabel KullaniciIconLabelBox;
    private JScrollPane MesajlasmaKutusu;
    private JTextPane MesajlasmaTextPanel;
    private StyledDocument MesajlasmaStilDoc;
    private JTextField MesajTextBox;
    private JPanel GonderPanel;
    private JLabel GonderLabel;
    private JLabel AktifKullaniciLabel;
    private JPanel LabellarinPanelleri;
    private JLabel BaglantiDurumLabel;
    private String kullanici;
    private PrintWriter serverOutput;
    private Point mouseDurum = null;
    Server sohbetServer = new Server();
    
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> {
            try 
            {
                //Server'i başlatan ve Client GUI'sini oluşturan fonksiyon çağırılır.
                new Client().ArayuzOlustur();
            }
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        });
    }

    //Client ve Giriş Panel arayüzü oluşturulur. Server "Aktif Durum"a geçer.
    private void ArayuzOlustur() throws IOException 
    {
        //GUI objeleri oluşturulur.
        //Client'in ana arayüzünü oluşturan objeler oluşturulur.
        ArayuzFrame = new JFrame("Client - Grup Sohbeti");
        TopBarPanel = new JPanel();
        KapatPanel = new JPanel();
        KapatLabel = new JLabel("X");
        MinimizePanel = new JPanel();
        MinimizeLabel = new JLabel("-");
        BaslikPanel = new JPanel();
        IconLabelBox = new JLabel();
        BaslikLabel = new JLabel("Client | Grup Sohbeti");
        MesajlasmaTextPanel = new JTextPane();
        MesajlasmaKutusu = new JScrollPane(MesajlasmaTextPanel);
        MesajTextBox = new JTextField();
        GonderPanel = new JPanel();
        GonderLabel = new JLabel("Gönder");
        LabellarinPanelleri = new JPanel();
        AktifKullaniciLabel = new JLabel();
        BaglantiDurumLabel = new JLabel();
        ImageIcon ChatIcon = new ImageIcon(Client.class.getResource("/Main/Resources/Chat.png"));
        Image ChatIconResized = ChatIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon ClientIconV2 = new ImageIcon(ChatIconResized);
        GroupLayout KapatPanelLayout = new GroupLayout(KapatPanel);
        GroupLayout MinimizePanelLayout = new GroupLayout(MinimizePanel);
        GroupLayout BaslikPanelLayout = new GroupLayout(BaslikPanel);
        GroupLayout TopBarPanelLayout = new GroupLayout(TopBarPanel);
        GroupLayout GonderPanelLayout = new GroupLayout(GonderPanel);
        GroupLayout LabellarinPanelleriLayout = new GroupLayout(LabellarinPanelleri);
        
        //Kullanıcı'nın giriş yaptığı kısmın arayüzünü oluşturan objeler oluşturulur.
        KullaniciGirisFrame = new JFrame("Kullanıcı Giriş Paneli");
        GirisTopBarPanel = new JPanel();
        GirisKapatPanel = new JPanel();
        GirisKapatLabel = new JLabel("X");
        GirisBaslikPanel = new JPanel();
        GirisBaslikLabel = new JLabel("Kullanıcı Giriş Paneli");
        GirisIconLabelBox = new JLabel();
        KullaniciAdiTextBox = new JTextField();
        GirisYapPanel = new JPanel();
        GirisYapLabel = new JLabel("Giriş Yap");
        KullaniciAdiGirLabel = new JLabel("Lütfen Kullanıcı Adınızı Giriniz");
        HosgeldinizLabel = new JLabel("Grup Sohbeti'ne Hoşgeldiniz!!");
        KullaniciIconLabelBox = new JLabel();
        ImageIcon UserIcon = new ImageIcon(Client.class.getResource("/Main/Resources/User.png"));
        Image UserIconResized = UserIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon UserIconV2 = new ImageIcon(UserIconResized);
        GroupLayout GirisKapatPanelLayout = new GroupLayout(GirisKapatPanel);
        GroupLayout GirisBaslikPanelLayout = new GroupLayout(GirisBaslikPanel);
        GroupLayout GirisTopBarPanelLayout = new GroupLayout(GirisTopBarPanel);
        GroupLayout GirisYapPanelLayout = new GroupLayout(GirisYapPanel);
        
        //Sohbet Arayüzü objelerine ayarlamalar yapılır.
        ArayuzFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ArayuzFrame.setSize(392, 346);
        ArayuzFrame.setAlwaysOnTop(true);
        ArayuzFrame.setLayout(null);
        ArayuzFrame.setLocationRelativeTo(null);
        ArayuzFrame.setUndecorated(true);
        ArayuzFrame.setIconImage(ChatIcon.getImage());
        ArayuzFrame.setBackground(Color.DARK_GRAY);
        ArayuzFrame.getContentPane().setBackground(Color.DARK_GRAY);
        KapatLabel.setHorizontalAlignment(SwingConstants.CENTER);
        KapatLabel.setToolTipText("Aktif Client'i Kapatır!");
        KapatLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        KapatLabel.setForeground(Color.BLACK);
        KapatLabel.setMaximumSize(new Dimension(30, 30));
        KapatLabel.setMinimumSize(new Dimension(30, 30));
        KapatLabel.setPreferredSize(new Dimension(30, 30));
        KapatPanel.setBackground(new Color(191, 191, 191));
        KapatPanel.setMaximumSize(new Dimension(30, 30));
        KapatPanel.setMinimumSize(new Dimension(30, 30));
        KapatPanel.setPreferredSize(new Dimension(30, 30));
        KapatPanel.setLayout(KapatPanelLayout);
        KapatPanelLayout.setHorizontalGroup(KapatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, KapatPanelLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(KapatLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
        KapatPanelLayout.setVerticalGroup(KapatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(KapatLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        MinimizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        MinimizeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        MinimizeLabel.setForeground(Color.BLACK);
        MinimizeLabel.setToolTipText("Aktif Client'i Simge Durumuna Küçültür!");
        MinimizeLabel.setMaximumSize(new Dimension(30, 30));
        MinimizeLabel.setMinimumSize(new Dimension(30, 30));
        MinimizeLabel.setPreferredSize(new Dimension(30, 30));
        MinimizePanel.setBackground(new Color(191, 191, 191));
        MinimizePanel.setMaximumSize(new Dimension(30, 30));
        MinimizePanel.setMinimumSize(new Dimension(30, 30));
        MinimizePanel.setPreferredSize(new Dimension(30, 30));
        MinimizePanel.setLayout(MinimizePanelLayout);
        MinimizePanelLayout.setHorizontalGroup(MinimizePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, MinimizePanelLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(MinimizeLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
        MinimizePanelLayout.setVerticalGroup(MinimizePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(MinimizeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        BaslikLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        BaslikLabel.setForeground(Color.BLACK);
        BaslikPanel.setBackground(new Color(191, 191, 191));
        BaslikPanel.setLayout(BaslikPanelLayout);
        BaslikPanelLayout.setHorizontalGroup(BaslikPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(BaslikPanelLayout.createSequentialGroup().addComponent(IconLabelBox).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(BaslikLabel, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)));
        BaslikPanelLayout.setVerticalGroup(BaslikPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, BaslikPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(IconLabelBox, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addComponent(BaslikLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)));
        IconLabelBox.setIcon(ClientIconV2);
        TopBarPanel.setBounds(0, 0, 392, 30);
        TopBarPanel.setBackground(new Color(191, 191, 191));
        TopBarPanel.setLayout(TopBarPanelLayout);
        TopBarPanelLayout.setHorizontalGroup(TopBarPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, TopBarPanelLayout.createSequentialGroup().addComponent(BaslikPanel, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE).addComponent(MinimizePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(KapatPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
        TopBarPanelLayout.setVerticalGroup(TopBarPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(MinimizePanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(KapatPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(BaslikPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        MesajlasmaTextPanel.setFocusable(false);
        MesajlasmaTextPanel.setBackground(new Color(102, 102, 102));
        MesajlasmaKutusu.setBounds(10, 40, 372, 245);
        MesajlasmaKutusu.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        MesajlasmaKutusu.setBackground(new Color(102, 102, 102));
        MesajlasmaKutusu.setBorder(new javax.swing.border.LineBorder(new Color(255, 51, 51), 1, true));
        MesajTextBox.setBounds(10, 310, 305, 26);
        MesajTextBox.setBackground(Color.LIGHT_GRAY);
        MesajTextBox.setForeground(Color.BLACK);
        MesajTextBox.setCaretColor(Color.BLACK);
        MesajTextBox.setToolTipText("Karşı tarafa söylemek istediğin şeyleri yazmak için BANA TIKLA ve Yazmaya Başla!!");
        GonderLabel.setForeground(Color.BLACK);
        GonderLabel.setBackground(Color.DARK_GRAY);
        GonderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GonderLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        GonderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        GonderLabel.setToolTipText("Sol kısıma yazdığınız Mesajlarınızı karşı tarafa iletmek için BANA TIKLA!!");
        GonderPanel.setBounds(318, 310, 65,26);
        GonderPanel.setBackground(Color.LIGHT_GRAY);
        GonderPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(51, 51, 51)), BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        GonderPanel.setLayout(GonderPanelLayout);
        GonderPanelLayout.setHorizontalGroup(GonderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(GonderLabel, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE));
        GonderPanelLayout.setVerticalGroup(GonderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(GonderLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        BaglantiDurumLabel.setForeground(Color.GREEN);
        BaglantiDurumLabel.setOpaque(false);
        BaglantiDurumLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        BaglantiDurumLabel.setText("Bağlantı Durumu: Aktif!");
        BaglantiDurumLabel.setToolTipText("Server ile olan bağlantı durumunuzu gösterir.");
        AktifKullaniciLabel.setForeground(Color.CYAN);
        AktifKullaniciLabel.setOpaque(false);
        AktifKullaniciLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        AktifKullaniciLabel.setPreferredSize(new Dimension(242, 26));
        LabellarinPanelleri.setBounds(10, 283, 372, 26);
        LabellarinPanelleri.setBackground(Color.DARK_GRAY);
        LabellarinPanelleri.setLayout(LabellarinPanelleriLayout);
        LabellarinPanelleriLayout.setHorizontalGroup(LabellarinPanelleriLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(LabellarinPanelleriLayout.createSequentialGroup().addComponent(AktifKullaniciLabel, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(BaglantiDurumLabel)));
        LabellarinPanelleriLayout.setVerticalGroup(LabellarinPanelleriLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(LabellarinPanelleriLayout.createSequentialGroup().addGroup(LabellarinPanelleriLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(AktifKullaniciLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(BaglantiDurumLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));
        MesajlasmaStilDoc = MesajlasmaTextPanel.getStyledDocument();
        ArayuzFrame.add(TopBarPanel);
        ArayuzFrame.add(MesajlasmaKutusu);
        ArayuzFrame.add(GonderPanel);
        ArayuzFrame.add(MesajTextBox);
        ArayuzFrame.add(LabellarinPanelleri);
        
        //Kullanıcı Giriş Paneli'nin GUI objelerinin ayarlamalarının yapıldığı kısım.
        KullaniciGirisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        KullaniciGirisFrame.setSize(320,130);
        KullaniciGirisFrame.setAlwaysOnTop(true);
        KullaniciGirisFrame.setLayout(null);
        KullaniciGirisFrame.setLocationRelativeTo(null);
        KullaniciGirisFrame.setUndecorated(true);
        KullaniciGirisFrame.setBackground(Color.GRAY);
        KullaniciGirisFrame.getContentPane().setBackground(new Color(140, 140, 140));
        KullaniciGirisFrame.setIconImage(UserIcon.getImage());
        GirisKapatLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GirisKapatLabel.setToolTipText("Giriş İşlemini İptal Et ve Kapat!");
        GirisKapatLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        GirisKapatLabel.setForeground(Color.BLACK);
        GirisKapatLabel.setMaximumSize(new Dimension(30, 30));
        GirisKapatLabel.setMinimumSize(new Dimension(30, 30));
        GirisKapatLabel.setPreferredSize(new Dimension(30, 30));
        GirisKapatPanel.setBackground(new Color(191, 191, 191));
        GirisKapatPanel.setMaximumSize(new Dimension(30, 30));
        GirisKapatPanel.setMinimumSize(new Dimension(30, 30));
        GirisKapatPanel.setPreferredSize(new Dimension(30, 30));
        GirisKapatPanel.setLayout(GirisKapatPanelLayout);
        GirisKapatPanelLayout.setHorizontalGroup(GirisKapatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, GirisKapatPanelLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(GirisKapatLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
        GirisKapatPanelLayout.setVerticalGroup(GirisKapatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(GirisKapatLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        GirisBaslikLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        GirisBaslikLabel.setForeground(Color.BLACK);
        GirisBaslikPanel.setBackground(new Color(191, 191, 191));
        GirisBaslikPanel.setLayout(GirisBaslikPanelLayout);
        GirisBaslikPanelLayout.setHorizontalGroup(GirisBaslikPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GirisBaslikPanelLayout.createSequentialGroup().addComponent(GirisIconLabelBox).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(GirisBaslikLabel, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)));
        GirisBaslikPanelLayout.setVerticalGroup(GirisBaslikPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, GirisBaslikPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(GirisIconLabelBox, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addComponent(GirisBaslikLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)));
        GirisIconLabelBox.setIcon(UserIconV2);
        GirisTopBarPanel.setBounds(0, 0, 320, 30);
        GirisTopBarPanel.setBackground(new Color(191, 191, 191));
        GirisTopBarPanel.setLayout(GirisTopBarPanelLayout);
        GirisTopBarPanelLayout.setHorizontalGroup(GirisTopBarPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, GirisTopBarPanelLayout.createSequentialGroup().addComponent(GirisBaslikPanel, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE).addComponent(GirisKapatPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
        GirisTopBarPanelLayout.setVerticalGroup(GirisTopBarPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(GirisKapatPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(GirisBaslikPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        KullaniciIconLabelBox.setIcon(UserIcon);
        KullaniciIconLabelBox.setBounds(10, 50, 50, 50);
        KullaniciAdiGirLabel.setForeground(Color.BLACK);
        KullaniciAdiGirLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        KullaniciAdiGirLabel.setBounds(70, 35, 240, 26);
        HosgeldinizLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        HosgeldinizLabel.setForeground(Color.BLACK);
        HosgeldinizLabel.setBounds(15, 95, 190, 26);
        KullaniciAdiTextBox.setBackground(Color.LIGHT_GRAY);
        KullaniciAdiTextBox.setForeground(Color.BLACK);
        KullaniciAdiTextBox.setCaretColor(Color.BLACK);
        KullaniciAdiTextBox.setBounds(70, 62, 240, 26);
        GirisYapLabel.setForeground(Color.BLACK);
        GirisYapLabel.setBackground(Color.DARK_GRAY);
        GirisYapLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GirisYapLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        GirisYapLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        GirisYapLabel.setToolTipText("Kullanıcı Adınızı onaylamak için BANA TIKLA!");
        GirisYapPanel.setBackground(Color.LIGHT_GRAY);
        GirisYapPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(51, 51, 51)), BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        GirisYapPanel.setBounds(220, 95, 90, 26);
        GirisYapPanel.setLayout(GirisYapPanelLayout);
        GirisYapPanelLayout.setHorizontalGroup(GirisYapPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(GirisYapLabel, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE));
        GirisYapPanelLayout.setVerticalGroup(GirisYapPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(GirisYapLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        KullaniciGirisFrame.add(GirisTopBarPanel);
        KullaniciGirisFrame.add(KullaniciIconLabelBox);
        KullaniciGirisFrame.add(KullaniciAdiGirLabel);
        KullaniciGirisFrame.add(HosgeldinizLabel);
        KullaniciGirisFrame.add(KullaniciAdiTextBox);
        KullaniciGirisFrame.add(GirisYapPanel);

        //Kapat Label'a Mouse Hover-Leave ve Click Event'lerini tanımlıyoruz.
        KapatLabel.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                KapatLabelMouseClicked(evt);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                KapatLabelMouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                KapatLabelMouseExited(evt);
            }
        });
        
        //Kullanıcı Girişi Paneli'ndeki Kapat Label'a Mouse Hover-Leave ve Click Event'lerini tanımlıyoruz. (İki Paneldede Kapatma işlevi aynı olduğu için tek bir fonksiyon kullandım, Hover-Leave için ayrı fonksiyonlar kullandım.)
        GirisKapatLabel.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                KapatLabelMouseClicked(evt);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                GirisKapatLabelMouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                GirisKapatLabelMouseExited(evt);
            }
        });
        
        //Minimize Label'a Mouse Hover-Leave Event'lerini tanımlıyoruz.
        MinimizeLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                MinimizeLabelMouseClicked(evt);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                MinimizeLabelMouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                MinimizeLabelMouseExited(evt);
            }
        });
        
        //Frame'e hareket özelliği için Press-Relase ve Drag Event'leri tanımlıyoruz.
        TopBarPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e)
            {
                TopBarPanelMouseDragged(e);
            }
        });
        
        TopBarPanel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e)
            {
                TopBarPanelMousePressed(e);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e)
            {
                TopBarPanelMouseReleased(e);
            }
        });
        
        //Kullanıcı Girişi Paneli'ndeki Frame'e hareket özelliği için Press-Relase ve Drag Event'leri tanımlıyoruz. (TopBar Panel'in Mouse Dragged Eventi her arayüz için ayrı fonksiyon içerdiği için o kısım hariç diğerlerini ortak kullandım.)
        GirisTopBarPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e)
            {
                GirisTopBarPanelMouseDragged(e);
            }
        });
        
        GirisTopBarPanel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e)
            {
                TopBarPanelMousePressed(e);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e)
            {
                TopBarPanelMouseReleased(e);
            }
        });
        
        //Giriş Yap Label'a Mouse Hover-Leave ve Click Event'lerini tanımlıyoruz.
        GirisYapLabel.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                GirisYapLabelMouseClicked(evt);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                GirisYapLabelMouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                GirisYapLabelMouseExited(evt);
            }
        });
        
        //Kullanici'nin Kullanıcı Adını girdiği Text Box'a tıklandığında aktifleşecek Event'leri tanımlıyoruz.
        KullaniciAdiTextBox.addFocusListener(new java.awt.event.FocusAdapter() 
        {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt)
            {
                KullaniciAdiTextBoxFocusGained(evt);
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                KullaniciAdiTextBoxFocusLost(evt);
            }
        });
        
        //Kullanici'nin Kullanıcı Adını girdiği Text Box'tan Enter tuşuyla mesaj gönderimi için KeyPress Event'ini tanımlıyoruz.
        KullaniciAdiTextBox.addKeyListener(new java.awt.event.KeyAdapter()
        {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                KullaniciAdiTextBoxKeyPressed(evt);
            }
        });
        
        //Gonder Label'a Mouse Hover-Leave ve Click Event'lerini tanımlıyoruz.
        GonderLabel.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                GonderLabelMouseClicked(evt);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                GonderLabelMouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                GonderLabelMouseExited(evt);
            }
        });
        
        //Mesaj Kutusu'na tıklandığında aktifleşecek Event'leri tanımlıyoruz.
        MesajTextBox.addFocusListener(new java.awt.event.FocusAdapter() 
        {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt)
            {
                MesajTextBoxFocusGained(evt);
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                MesajTextBoxFocusLost(evt);
            }
        });
        
        //Mesaj Kutusu'ndan Enter tuşuyla mesaj gönderimi için KeyPress Event'ini tanımlıyoruz.
        MesajTextBox.addKeyListener(new java.awt.event.KeyAdapter()
        {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                MesajTextBoxKeyPressed(evt);
            }
        });
        
        //Server'i başlatan fonksiyonu çağırır.
        ServerBaslat();
        try 
        {
            //Server Adresi ve Port'u belirlenir.
            Socket Baglanti = new Socket("localhost", 2953);
            serverOutput = new PrintWriter(Baglanti.getOutputStream(), true);
            new Thread(new GelenMesajlar(Baglanti)).start();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        //Öncelikle Kullanıcı Girişi yapılabilmesi için Kullanıcı Giriş Frame'i çağırılır.
        KullaniciGirisFrame.setVisible(true);
    }

    //Kapat Label'a mouse ile tıkladığında uyuglamayı kapatır.
    private void KapatLabelMouseClicked(java.awt.event.MouseEvent evt)
    {                                        
        System.exit(0);
    }                                       

    //Kapat Label'ın üstüne mouse geldiğinde olacak görsellikler.
    private void KapatLabelMouseEntered(java.awt.event.MouseEvent evt)
    {                                        
        KapatPanel.setBackground(Color.RED);
        KapatLabel.setForeground(Color.WHITE);
    }                                       

    //Kapat Label'ın üstünden mouse gittiğinde yapılan görselliği eski haline getirir.
    private void KapatLabelMouseExited(java.awt.event.MouseEvent evt)
    {                                       
        KapatPanel.setBackground(new Color(191, 191, 191));
        KapatLabel.setForeground(Color.BLACK);
    }
    
    //Kullanıcı Giriş Paneli'ndeki Kapat Label'ın üstüne mouse geldiğinde olacak görsellikler.
    private void GirisKapatLabelMouseEntered(java.awt.event.MouseEvent evt)
    {                                        
        GirisKapatPanel.setBackground(Color.RED);
        GirisKapatLabel.setForeground(Color.WHITE);
    }                                       

    //Kullanıcı Giriş Paneli'ndeki Kapat Label'ın üstünden mouse gittiğinde yapılan görselliği eski haline getirir.
    private void GirisKapatLabelMouseExited(java.awt.event.MouseEvent evt)
    {                                       
        GirisKapatPanel.setBackground(new Color(191, 191, 191));
        GirisKapatLabel.setForeground(Color.BLACK);
    } 

    //Minimize Label'a mouse ile tıkladığında uyuglamayı simge durumuna küçültür.
    private void MinimizeLabelMouseClicked(java.awt.event.MouseEvent evt)
    {                                           
        ArayuzFrame.setExtendedState(JFrame.ICONIFIED);
    }                                          

    //Minimize Label'ın üstüne mouse geldiğinde olacak görsellikler.
    private void MinimizeLabelMouseEntered(java.awt.event.MouseEvent evt)
    {                                           
        MinimizePanel.setBackground(new Color(122, 122, 122));
        MinimizeLabel.setForeground(Color.WHITE);
    }                                          

    //Minimize Label'ın üstünden mouse gittiğinde yapılan görselliği eski haline getirir.
    private void MinimizeLabelMouseExited(java.awt.event.MouseEvent evt)
    {               
        MinimizePanel.setBackground(new Color(191, 191, 191));
        MinimizeLabel.setForeground(Color.BLACK);
    }
    
    //TopBar Panel ile Frame'in hareketinin sağlandığı kısım.
    private void TopBarPanelMousePressed(java.awt.event.MouseEvent e)
    {                                         
        mouseDurum = e.getPoint();
    }                                        

    private void TopBarPanelMouseReleased(java.awt.event.MouseEvent e)
    {                                          
        mouseDurum = null;
    }
    
    //Sohbet'in bulunduğu Frame'i hareket ettiren kısım.
    private void TopBarPanelMouseDragged(java.awt.event.MouseEvent e)
    {                                         
        Point koordinatlar = e.getLocationOnScreen();
        ArayuzFrame.setLocation(koordinatlar.x - mouseDurum.x, koordinatlar.y - mouseDurum.y);
    }
    
    //Kullanıcı girişinin bulunduğu Frame'i hareket ettiren kısım.
    private void GirisTopBarPanelMouseDragged(java.awt.event.MouseEvent e)
    {                                         
        Point koordinatlar = e.getLocationOnScreen();
        KullaniciGirisFrame.setLocation(koordinatlar.x - mouseDurum.x, koordinatlar.y - mouseDurum.y);
    }
    
    //GirisYap Label'a tıkladığımızda Kullanıcı Adını değişkene yazar. Kullanıcı adı gerekli şartları sağlamıyorsa "Misafir.Kullanici" adı tanımlanır. Sonrasında Sohbet Frame'i açılır.
    private void GirisYapLabelMouseClicked(java.awt.event.MouseEvent evt)
    {
        kullanici = KullaniciAdiTextBox.getText();
        if (kullanici == null || kullanici.isBlank())
            kullanici = "Misafir.Kullanici";
        serverOutput.println(kullanici + " Sohbet'e Katıldı!");
        AktifKullaniciLabel.setText("Kullanıcı Adınız: " + kullanici);
        AktifKullaniciLabel.setToolTipText("Mevcut Client'teki (sizin) kullanici adinizi gösterir: " + kullanici);
        KullaniciGirisFrame.setVisible(false);
        ArayuzFrame.setVisible(true);
    }
    
    //GirisYap Label'ın üstüne mouse geldiğinde olacak görsellikler.
    private void GirisYapLabelMouseEntered(java.awt.event.MouseEvent evt)
    {                                         
        GirisYapPanel.setBackground(new Color(154, 218, 223));
        GirisYapLabel.setForeground(Color.BLACK);
        GirisYapLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }                                        

    //GirisYap Label'ın üstünden mouse gittiğinde yapılan görselliği eski haline getirir.
    private void GirisYapLabelMouseExited(java.awt.event.MouseEvent evt)
    {                                        
        GirisYapPanel.setBackground(Color.LIGHT_GRAY);
        GirisYapLabel.setForeground(Color.BLACK);
        GirisYapLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    //Mesaj Kutusu'nda Enter Tuşuna bastığımızda Kullanıcı Adını değişkene yazar. Kullanıcı adı gerekli şartları sağlamıyorsa "Misafir.Kullanici" adı tanımlanır. Sonrasında Sohbet Frame'i açılır.
    private void KullaniciAdiTextBoxKeyPressed(java.awt.event.KeyEvent evt)
    {                                        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            kullanici = KullaniciAdiTextBox.getText();
            if (kullanici == null || kullanici.isBlank())
                kullanici = "Misafir.Kullanici";
            serverOutput.println(kullanici + " Sohbet'e Katıldı!");
            AktifKullaniciLabel.setText("Kullanıcı Adınız: " + kullanici);
            AktifKullaniciLabel.setToolTipText("Mevcut Client'teki (sizin) kullanici adinizi gösterir: " + kullanici);
            KullaniciGirisFrame.setVisible(false);
            ArayuzFrame.setVisible(true);
        }
    }
    
    //Mesaj Kutusu'na Tıklandığında Mesaj kutusu çerçevesi Mavi rengini alır ve pasif duyumdayken olan tüm efektleri eski haline çevirir.
    private void KullaniciAdiTextBoxFocusGained(java.awt.event.FocusEvent evt)
    {
        KullaniciAdiTextBox.setText("");
        KullaniciAdiTextBox.setBackground(new Color(200, 218, 223));
        KullaniciAdiTextBox.setForeground(Color.BLACK);
        KullaniciAdiTextBox.setBorder(new javax.swing.border.LineBorder(new Color(104, 200, 208), 2, true));
    }                                        

    //Tıklama odağı kaybolduğunda çerçeve eski haline döner ve Silip bir şekilde "Mesaj yazmak için tıklayınız" yazısı belirir.
    private void KullaniciAdiTextBoxFocusLost(java.awt.event.FocusEvent evt)
    {
        KullaniciAdiTextBox.setText("Kullanıcı Adınızı girmek için tıklayınız...");
        KullaniciAdiTextBox.setForeground(new Color(154, 218, 223));
        KullaniciAdiTextBox.setBackground(new Color(72, 72, 72));
        KullaniciAdiTextBox.setBorder(new javax.swing.border.LineBorder(Color.DARK_GRAY, 1, true));
    }
    
    //Gonder Label'a tıkladığımızda karşı tarafa Mesaj Kutusu'ndaki mesajları iletir.
    private void GonderLabelMouseClicked(java.awt.event.MouseEvent evt)
    {
        if (serverOutput != null)
        {
            String mesaj = MesajTextBox.getText();
            serverOutput.println(kullanici + ": " + mesaj);
            MesajTextBox.setText("");
        }
    }
    
    //Gönder Label'ın üstüne mouse geldiğinde olacak görsellikler.
    private void GonderLabelMouseEntered(java.awt.event.MouseEvent evt)
    {                                         
        GonderPanel.setBackground(new Color(154, 218, 223));
        GonderLabel.setForeground(Color.BLACK);
        GonderLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }                                        

    //Gönder Label'ın üstünden mouse gittiğinde yapılan görselliği eski haline getirir.
    private void GonderLabelMouseExited(java.awt.event.MouseEvent evt)
    {                                        
        GonderPanel.setBackground(Color.LIGHT_GRAY);
        GonderLabel.setForeground(Color.BLACK);
        GonderLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    //Mesaj Kutusu'nda Enter Tuşuna bastığımızda karşı tarafa Mesaj Kutusu'ndaki mesajları iletir.
    private void MesajTextBoxKeyPressed(java.awt.event.KeyEvent evt)
    {                                        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if (serverOutput != null)
            {
                String mesaj = MesajTextBox.getText();
                serverOutput.println(kullanici + ": " + mesaj);
                MesajTextBox.setText("");
            }
        }
    }
    
    //Mesaj Kutusu'na Tıklandığında Mesaj kutusu çerçevesi Mavi rengini alır ve pasif duyumdayken olan tüm efektleri eski haline çevirir.
    private void MesajTextBoxFocusGained(java.awt.event.FocusEvent evt)
    {
        MesajTextBox.setText("");
        MesajTextBox.setBackground(new Color(200, 218, 223));
        MesajTextBox.setForeground(Color.BLACK);
        MesajTextBox.setBorder(new javax.swing.border.LineBorder(Color.CYAN, 2, true));
    }                                        

    //Tıklama odağı kaybolduğunda çerçeve eski haline döner ve Silip bir şekilde "Mesaj yazmak için tıklayınız" yazısı belirir.
    private void MesajTextBoxFocusLost(java.awt.event.FocusEvent evt)
    {
        MesajTextBox.setText("Mesaj yazmak için tıklayınız...");
        MesajTextBox.setForeground(new Color(217, 217, 217));
        MesajTextBox.setBackground(new Color(141, 141, 141));
        MesajTextBox.setBorder(new javax.swing.border.LineBorder(Color.DARK_GRAY, 2, true));
    }
    
    private void ServerBaslat()
    {
        // Server başlatılır.
        new Thread(() -> {
            sohbetServer.Baslat();
        }).start();
    }

    //Diğer Client'lerden gelen mesajları ekrana yazdıran fonksiyon.
    private class GelenMesajlar implements Runnable 
    {
        private Scanner serverInput;
        
        public GelenMesajlar(Socket socket) throws IOException 
        {
            this.serverInput = new Scanner(socket.getInputStream());
        }

        @Override
        public void run() 
        {
            try
            {
                while (true)
                {
                    if (serverInput.hasNextLine()) 
                    {
                        String mesaj = serverInput.nextLine();
                        EkranaYazdir(mesaj);
                    }
                }
            }
            finally
            {
                serverInput.close();
            }
        }

        //Mesajları ekrana yazdıran fonksiyon.
        private void EkranaYazdir(String mesaj) 
        {
            SwingUtilities.invokeLater(() -> {
                try
                {
                    //Mesajların görselliğini ayarlayabildiğimiz kısım.
                    SimpleAttributeSet MesajStili = new SimpleAttributeSet();
                    StyleConstants.setForeground(MesajStili, Color.WHITE);
                    StyleConstants.setFontSize(MesajStili, 12);
                    MesajlasmaStilDoc.insertString(MesajlasmaStilDoc.getLength(), mesaj + "\n", MesajStili);
                }
                catch (BadLocationException e)
                {
                    e.printStackTrace();
                }
            });
        }
    }
}
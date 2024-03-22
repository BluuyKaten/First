package com.gzlg.ui;



import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;


public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //JFrame 界面，窗体
    //子类呢？ 也表示界面，窗体
    //规定：GameJFrame这个界面表示的激素游戏的主界面
    //以后跟游戏相关的所以逻辑都写在这个类中

    //4.创建一个二维数组
    //目的:用来管理数据
    //加载图片的时候,会根据二维数组中的数据进行加载
    int[][] date = new int[4][4];

    //记录空白方块在二维数组中的位置
    int x= 0;
    int y=0;

    //定义一个变量,记录当前展示图片的路径
    String path="puzzlegame\\image\\animal\\animal1\\";

    //定义一个二维数组,存储正确的数据
    int[][] win = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };

    //定义变量用来统计步数
    int step =0;

    //创建选项下面的条目对象
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("公众号");

    JMenuItem animal = new JMenuItem("动物");


    public GameJFrame() {
        //初始化界面
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化数据(打乱)
        initDate();


        //初始化图片（根据打乱之后的结果去加载数据）
        initImage();

        //让界面显示出来,建议写在最后
        this.setVisible(true);
    }


    //初始化数据(打乱)
    private void initDate() {
        //1.定义一个一维数组
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        //2.打乱数组中的数据的顺序
        //遍历数组,得到每一个元素,拿着每一个元素跟随机索引上的数据进行交换
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            //获取到随机索引
            int index = r.nextInt(tempArr.length);
            //拿着遍历到的每一个数据,跟随机索引上的数据进行交换
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }


        //3.给二维数组添加数据
        //解放一;
        //遍历一维数组tempArr得到每一个元素,把每一个元素依次添加到二维数组当中
        for (int i = 0; i < tempArr.length; i++) {
            if(tempArr[i] == 0){
                x=i/4;
                y=i%4;
            }
            date[i / 4][i % 4] = tempArr[i];
        }

    }

    //初始化图片
    //添加图片的时候,就需要按照二维数组中管理的数据添加图片
    private void initImage() {

        //清空原本已经出现的所以图片
        this.getContentPane().removeAll();

        if (victory()) {
            //显示胜利的图标
            JLabel winJLable = new JLabel(new ImageIcon("puzzlegame\\image\\win.png"));
            winJLable.setBounds(203,283,197,73);
            this.getContentPane().add(winJLable);
        }

        JLabel stepCount = new JLabel("步数:"+step);
        stepCount.setBounds(50,50,100,20);
        stepCount.setForeground(Color.WHITE);
        this.getContentPane().add(stepCount);

        //路径分为两种：
        //绝对路径：一定是从盘符开始的。 C:\
        //相对路径:不是从盘符开始的
        //相对路径相对当前项目而言的。 aaa\\bbb
        //在当前项目下,去找aaa文件夹,里面再去找bbb文件夹。


        //细节：
        //先加载的图片在上方,后加载的图片塞在下面。


        //外循环 ---把内循环重复循环了4次
        for (int i = 0; i < 4; i++) {
            //内循环 ---表示在一行添加4张图片
            for (int j = 0; j < 4; j++) {
                //获取当前要加载图片的序号
                int num = date[i][j];
                //创建一个JLabel的对象（管理容器）
                JLabel jLabel1 = new JLabel(new ImageIcon(path + num + ".jpg"));
                //指定图片位置
                jLabel1.setBounds(105 * j + 83, 105 * i+134, 105, 105);
                //给平台添加边框
                //0:让图片凸起来
                //1: 表示让图片凹下去
                jLabel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
                //把管理容器添加到界面中
                this.getContentPane().add(jLabel1);
            }

        }


        //添加背景图片
        JLabel background = new JLabel(new ImageIcon("puzzlegame\\image\\background.png"));
        background.setBounds(40,40,508,560);
        //把背景图片添加到界面当中
        this.getContentPane().add(background);

        //刷新一下界面
        this.getContentPane().repaint();
    }


    private void initJMenuBar() {
        //创建整个的菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        //创建菜单上面的两个选项的对象（功能       关于我们）
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        JMenu changeImage = new JMenu("更换图片");

        //将每一个选项下面的条目添加到选项当中
        functionJMenu.add(changeImage);
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);


        aboutJMenu.add(accountItem);

        changeImage.add(animal);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        animal.addActionListener(this);

        //将菜单里面的两个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        //设置界面的宽高
        this.setSize(603, 680);
        //设置界面的标题
        this.setTitle("拼图游戏 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的居中放置,只有取消了才会按照XY轴的形式添加组件
        this.setLayout(null);
        //给整个界面添加键盘监听事件
        this.addKeyListener(this);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //按下不松时会调用这个方法

    @Override
    public void keyPressed(KeyEvent e) {
        int code =e.getKeyCode();
        if(code == 65){
            //把界面中所以的图片全部删除
            this.getContentPane().removeAll();
            //加载第一张完整图片
            JLabel all = new JLabel(new ImageIcon(path+"all.png"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            //加载背景图片
            JLabel background = new JLabel(new ImageIcon("puzzlegame\\image\\background.png"));
            background.setBounds(40,40,508,560);
            //把背景图片添加到界面当中
            this.getContentPane().add(background);
            //刷新界面
            this.getContentPane().repaint();
        }


    }

    //松开按键的时候会调用这个方法
    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利,如果胜利,此方法需要直接结束,不能再执行下面的移动代码了
        if(victory()){
            //结束方法
            return;
        }

        //对上、下、左、右进行判断
        //左：37 上:38 右：39 下：40 怎么知道的呢？
        int code = e.getKeyCode();
        System.out.println(code);
        //由下面的代码可知
//        System.out.println(code);
        if(code == 37){
            if(y == 3){
                return;
            }
            System.out.println("向左移动");
            //逻辑：
            //把空白方块右方的数字往左移动
            date[x][y]= date[x][y+1];
            date[x][y+1] = 0;
            y++;
            //每一动一次,让计数器自增一次
            step++;
            //调用方法按照最新的数字去加载图片
            initImage();


        }else if (code == 38){
            System.out.println("向上移动");
            if(x==3){
                //表示空白方块已经在最下方了，他的下面没有图片再能移动了.
                return;
            }
            //逻辑：
            //把空白方块下方的数字往上移动
            //x,y 表示空白方块
            //x+1, y 表示空白方块下方的数字

            //把空白方块下方的数字赋值给空白方块
            //3.0 3,1 3,2 3,3
            date[x][y]= date[x+1][y];
            date[x+1][y] = 0;
            x++;
            //每一动一次,让计数器自增一次
            step++;
            //调用方法按照最新的数字去加载图片
            initImage();


        }else if (code == 39){
            System.out.println("向右移动");
            if(y == 0){
                return;
            }
            //逻辑：
            //把空白方块左方的数字往右移动
            date[x][y]= date[x][y-1];
            date[x][y-1] = 0;
            y--;
            //每一动一次,让计数器自增一次
            step++;
            //调用方法按照最新的数字去加载图片
            initImage();
        }else if (code == 40){
            System.out.println("向下移动");
            if(x == 0){
                return;
            }
            //逻辑：
            //把空白方块上方的数字往下移动
            date[x][y]= date[x-1][y];
            date[x-1][y] = 0;
            x--;
            //每一动一次,让计数器自增一次
            step++;
            //调用方法按照最新的数字去加载图片
            initImage();

        } else if (code == 65) {
            initImage();
        } else if (code == 87) {
            date = new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
            initImage();
        }
    }

    //判断date数组中的数据是否跟win数组中相同
    //如果全部相同,返回true,否则返回false
    public boolean victory(){
        for (int i = 0; i < date.length; i++) {
            //i:表示二维数组 date里面的索引
            //date[i]:依次表示每一个一维数组
            for (int j = 0; j < date[i].length; j++) {
                if(date[i][j] != win[i][j]){
                    //只要有一个数据不一样,则返回false
                    return false;
                }

            }
        }
        //循环结束表示数组遍历比较完毕,全部一样返回true
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前被点击的条目对象
        Object obj = e.getSource();
        //判断
        if(obj == replayItem){
            System.out.println("重新游戏");

            //计步器清零
            step = 0;
            //再次打乱二维数组的中数据
            initDate();
            //重新加载图片
            initImage();



        } else if (obj == reLoginItem) {
            System.out.println("重新登录");
            //关闭当前游戏界面
            this.setVisible(false);
            //打开登录界面
            new LoginJFrame();
        } else if (obj ==closeItem) {
            System.out.println("关闭游戏");
            //直接关闭虚拟机即可
            System.exit(0);
        } else if (obj == accountItem) {
            System.out.println("公众号");

            //创建一个弹框对象
            JDialog jDialog = new JDialog();
            //创建一个管理图片的容器对象jLable
            JLabel jLabel = new JLabel(new ImageIcon("puzzlegame\\image\\about.png"));
            //设置位置和宽高
            jLabel.setBounds(0,0,258,258);
            //把图片统计到弹框当中
            jDialog.getContentPane().add(jLabel);
            //给弹框设置大小
            jDialog.setSize(260,280);
            //让弹框指定
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //让弹框不关闭则无法操作下面的界面
            jDialog.setModal(true);
            //让弹框显示出来
            jDialog.setVisible(true);
        } else if (obj == animal) {
            //随机选择图片

            //修改path变量记录的值

            //写一些重开一把的逻辑
        }
    }
}

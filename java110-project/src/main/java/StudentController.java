import java.util.Scanner;

public class StudentController {
    
    static Student[] students = new Student[100];
    static int studentIndex = 0;
    static Scanner keyIn;
    
    static class Student extends Member{
        protected String school;
        protected boolean working;
        protected String tel;
        
        public String getSchool() {
            return school;
        }
        public boolean isWorking() {
            return working;
        }
        public String getTel() {
            return tel;
        }
        public void setSchool(String school) {
            this.school = school;
        }
        public void setWorking(boolean working) {
            this.working = working;
        }
        public void setTel(String tel) {
            this.tel = tel;
        }
    }
    
    static void serviceStudentMenu() {
        while(true) {
            System.out.print("학생관리> ");
            String command = keyIn.nextLine();
            if(command.equals("list")) {
                printStudent();
            }   else if(command.equals("add")) {
                inputStudents();
            }   else if(command.equals("quit")){
                break;
            }   else {
                System.out.println("유효하지 않는 명령입니다.");
            }
        }
    }
    
    static void inputStudents() {
        while (true) {
            Student m = new Student();
            
            System.out.print("이름? ");
            m.setName(keyIn.nextLine());
            
            System.out.print("이메일? ");
            m.setEmail(keyIn.nextLine());
            
            System.out.print("암호? ");
            m.setPassword(keyIn.nextLine());
            
            System.out.print("최종 학력? ");
            m.setSchool(keyIn.nextLine());
            
            System.out.print("재직 여부?(true/false) ");
            m.setWorking(Boolean.parseBoolean(keyIn.nextLine()));
            
            System.out.print("전화번호? ");
            m.setTel(keyIn.nextLine());
            
            students[studentIndex++] = m;
            
            System.out.print("계속 하시겠습니까?(Y/n) ");
            String answer = keyIn.nextLine();
            if (answer.toLowerCase().equals("n"))
                break;
        }
    }
    
    static void printStudent() {
        int count = 0;
        for(Student s : students) {
            if(count++ == studentIndex)
                break;
            System.out.printf("%s, %s, %s, %s, %b, %s\n",
                    s.getName(), s.getEmail(), s.getPassword(),
                    s.getSchool(), s.isWorking(), s.getTel());
        }
    }
    
}
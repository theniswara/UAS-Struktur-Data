public class StudentLinkedList {
    // Inner class Student
    static class Student {
        String nim;
        String name;
        double gpa;
        Student next;

        Student(String nim, String name, double gpa) {
            this.nim = nim;
            this.name = name;
            this.gpa = gpa;
            this.next = null;
        }
    }

    private Student head;
    private int size;

    public StudentLinkedList() {
        head = null;
        size = 0;
    }

    // Insert di awal list
    public void insertFirst(String nim, String name, double gpa) {
        Student newStudent = new Student(nim, name, gpa);
        newStudent.next = head;
        head = newStudent;
        size++;
    }

    // Insert di akhir list
    public void insertLast(String nim, String name, double gpa) {
        Student newStudent = new Student(nim, name, gpa);
        if (head == null) {
            head = newStudent;
        } else {
            Student current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newStudent;
        }
        size++;
    }

    // Insert di posisi tertentu
    public void insertAt(int position, String nim, String name, double gpa) {
        if (position <= 0) {
            insertFirst(nim, name, gpa);
        } else if (position >= size) {
            insertLast(nim, name, gpa);
        } else {
            Student newStudent = new Student(nim, name, gpa);
            Student current = head;
            for (int i = 0; i < position - 1; i++) {
                current = current.next;
            }
            newStudent.next = current.next;
            current.next = newStudent;
            size++;
        }
    }

    // Hapus mahasiswa berdasarkan NIM
    public void deleteByNim(String nim) {
        if (head == null) return;
        if (head.nim.equals(nim)) {
            head = head.next;
            size--;
            return;
        }
        Student current = head;
        while (current.next != null && !current.next.nim.equals(nim)) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
            size--;
        }
    }

    // Cari mahasiswa berdasarkan NIM
    public Student search(String nim) {
        Student current = head;
        while (current != null) {
            if (current.nim.equals(nim)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Tampilkan semua data mahasiswa
    public void display() {
        System.out.println("=== Data Mahasiswa ===");
        Student current = head;
        while (current != null) {
            System.out.printf("NIM: %s, Nama: %s, IPK: %.2f\n", current.nim, current.name, current.gpa);
            current = current.next;
        }
    }

    // Return jumlah node dalam list
    public int size() {
        return size;
    }

    // Cek apakah list kosong
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Urutkan mahasiswa berdasarkan IPK secara descending menggunakan bubble sort pada linked list
   
     */
    public void sortByGPA() {
        if (head == null || head.next == null) return;
        boolean swapped;
        do {
            swapped = false;
            Student current = head;
            Student prev = null;
            while (current.next != null) {
                if (current.gpa < current.next.gpa) {
                    // Swap node
                    Student tmp = current.next;
                    current.next = tmp.next;
                    tmp.next = current;
                    if (prev == null) {
                        head = tmp;
                    } else {
                        prev.next = tmp;
                    }
                    prev = tmp;
                    swapped = true;
                } else {
                    prev = current;
                    current = current.next;
                }
            }
        } while (swapped);
    }

    /**
      Balik urutan linked list (yang pertama menjadi terakhir, dst.)
    
     */
    public void reverse() {
        Student prev = null;
        Student current = head;
        while (current != null) {
            Student next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }

    /**
     * Return Student dengan IPK tertinggi
     */
    public Student findHighestGPA() {
        if (head == null) return null;
        Student max = head;
        Student current = head.next;
        while (current != null) {
            if (current.gpa > max.gpa) {
                max = current;
            }
            current = current.next;
        }
        return max;
    }

    /**
     * Return array mahasiswa dengan IPK di atas threshold tertentu
     */
    public java.util.List<Student> getStudentsAboveGPA(double threshold) {
        java.util.List<Student> result = new java.util.ArrayList<>();
        Student current = head;
        while (current != null) {
            if (current.gpa > threshold) {
                result.add(current);
            }
            current = current.next;
        }
        return result;
    }

    /**
     * Gabungkan dua sorted linked list menjadi satu sorted linked list (descending by GPA)
     
     */
    public void mergeSortedList(StudentLinkedList otherList) {
        Student dummy = new Student("", "", 0);
        Student tail = dummy;
        Student a = this.head;
        Student b = otherList.head;
        while (a != null && b != null) {
            if (a.gpa >= b.gpa) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        if (a != null) tail.next = a;
        if (b != null) tail.next = b;
        // Hitung ulang size
        int newSize = 0;
        Student cur = dummy.next;
        while (cur != null) {
            newSize++;
            cur = cur.next;
        }
        this.head = dummy.next;
        this.size = newSize;
    }
}

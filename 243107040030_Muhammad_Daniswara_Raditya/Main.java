public class Main {
  public static void main(String[] args) {
    // StudentLinkedList 
    System.out.println("\n=== TEST StudentLinkedList ===");
    StudentLinkedList list = new StudentLinkedList();
    list.insertFirst("243107040031", "Aldan Haposan Arfah", 3.65);
    list.insertFirst("243107040030", "Muhammad Daniswara Raditya", 3.82);
    list.insertFirst("243107040000", "Andi Pratama", 3.75);
    list.display();
    System.out.println("Total mahasiswa: " + list.size());
    System.out.println();

    // Test insertLast
    list.insertLast("12348", "Dewi Lestari", 3.90);
    list.display();
    System.out.println("Total mahasiswa: " + list.size());
    System.out.println();

    // Test insertAt
    list.insertAt(2, "12349", "Rina Ayu", 3.70);
    list.display();
    System.out.println("Total mahasiswa: " + list.size());
    System.out.println();

    // Test search
    StudentLinkedList.Student s = list.search("12346");
    if (s != null) {
      System.out.println("Ditemukan: NIM: " + s.nim + ", Nama: " + s.name + ", IPK: " + s.gpa);
    } else {
      System.out.println("Mahasiswa tidak ditemukan");
    }
    System.out.println();

    // Test deleteByNim
    list.deleteByNim("12347");
    list.display();
    System.out.println("Total mahasiswa: " + list.size());
    System.out.println();

    // Test isEmpty
    System.out.println("Apakah list kosong? " + list.isEmpty());

    // CircularPlaylist 
    System.out.println("\n=== TEST CircularPlaylist ===");
    CircularPlaylist playlist = new CircularPlaylist();
    playlist.addSong("21 Guns", "Green Day", 321); // 5:21
    playlist.addSong("All I Ask", "Adele", 273); // 4:33
    playlist.addSong("It Will Rain", "Bruno Mars", 264);// 4:24

    playlist.displayPlaylist();
    System.out.println();
    playlist.playNext();
    playlist.displayPlaylist();
    System.out.println();
    playlist.playPrevious();
    playlist.displayPlaylist();
    System.out.println();
    playlist.shuffle();
    playlist.displayPlaylist();
    System.out.println();
    playlist.removeSong("All I Ask");
    playlist.displayPlaylist();

    // TextEditor (Undo/Redo Doubly Linked List)
    System.out.println("\n=== TEST TextEditor (Undo/Redo Doubly Linked List) ===");
    TextEditor editor = new TextEditor();
    editor.insertText("Hello World", 0);
    editor.insertText("Beautiful ", 6);
    System.out.println(editor.getCurrentText()); // "Hello Beautiful World"
    editor.undo();
    System.out.println(editor.getCurrentText()); // "Hello World"
    editor.redo();
    System.out.println(editor.getCurrentText()); // "Hello Beautiful World"
    editor.replaceText(6, 9, "Awesome ");
    System.out.println(editor.getCurrentText()); // "Hello Awesome World"
    editor.deleteText(6, 8);
    System.out.println(editor.getCurrentText()); // "Hello World"
    editor.undo();
    System.out.println(editor.getCurrentText()); // "Hello Awesome World"
    editor.getActionHistory();
  }
}

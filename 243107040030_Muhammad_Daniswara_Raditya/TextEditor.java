// Struktur data Action untuk menyimpan riwayat aksi (doubly linked list)
class Action {
    String type; // "INSERT", "DELETE", "REPLACE"
    String text;
    int position;
    String previousText; // untuk undo
    Action next;
    Action prev;

    Action(String type, String text, int position, String previousText) {
        this.type = type;
        this.text = text;
        this.position = position;
        this.previousText = previousText;
        this.next = null;
        this.prev = null;
    }
}

public class TextEditor {
    private StringBuilder content = new StringBuilder();
    private Action head = null; // head of action history
    private Action current = null; // pointer to current action (for undo/redo)

    // Insert text di posisi tertentu
    public void insertText(String text, int position) {
        if (position < 0) position = 0;
        if (position > content.length()) position = content.length();
        content.insert(position, text);
        Action action = new Action("INSERT", text, position, null);
        addAction(action);
    }

    // Hapus text dari posisi start sepanjang length
    public void deleteText(int start, int length) {
        if (start < 0) start = 0;
        if (start > content.length()) return;
        int end = Math.min(start + length, content.length());
        String deleted = content.substring(start, end);
        content.delete(start, end);
        Action action = new Action("DELETE", deleted, start, null);
        addAction(action);
    }

    // Replace text
    public void replaceText(int start, int length, String newText) {
        if (start < 0) start = 0;
        if (start > content.length()) return;
        int end = Math.min(start + length, content.length());
        String oldText = content.substring(start, end);
        content.replace(start, end, newText);
        Action action = new Action("REPLACE", newText, start, oldText);
        addAction(action);
    }

    // Undo aksi terakhir
    public void undo() {
        if (current == null) return;
        switch (current.type) {
            case "INSERT":
                content.delete(current.position, current.position + current.text.length());
                break;
            case "DELETE":
                content.insert(current.position, current.text);
                break;
            case "REPLACE":
                content.replace(current.position, current.position + current.text.length(), current.previousText);
                break;
        }
        current = current.prev;
    }

    // Redo aksi yang di-undo
    public void redo() {
        if (current == null) {
            if (head != null) current = head;
            else return;
        } else if (current.next != null) {
            current = current.next;
        } else {
            return;
        }
        switch (current.type) {
            case "INSERT":
                content.insert(current.position, current.text);
                break;
            case "DELETE":
                content.delete(current.position, current.position + current.text.length());
                break;
            case "REPLACE":
                content.replace(current.position, current.position + current.previousText.length(), current.text);
                break;
        }
    }

    // Return text saat ini
    public String getCurrentText() {
        return content.toString();
    }

    // Tampilkan riwayat aksi
    public void getActionHistory() {
        Action temp = head;
        int idx = 1;
        System.out.println("=== Action History ===");
        while (temp != null) {
            System.out.printf("%d. %s '%s' at %d\n", idx++, temp.type, temp.text, temp.position);
            temp = temp.next;
        }
    }

    // Menambah aksi ke riwayat (doubly linked list)
    private void addAction(Action action) {
        if (current != null && current.next != null) {
            // Hapus semua redo setelah current
            current.next.prev = null;
            current.next = null;
        }
        if (head == null) {
            head = action;
        } else {
            action.prev = current;
            if (current != null) current.next = action;
        }
        current = action;
    }
}

import java.util.*;

class Song {
  String title;
  String artist;
  int duration; // in seconds
  Song next;

  Song(String title, String artist, int duration) {
    this.title = title;
    this.artist = artist;
    this.duration = duration;
    this.next = null;
  }
}

public class CircularPlaylist {
  private Song head;
  private Song current;

  public void addSong(String title, String artist, int duration) {
    Song newSong = new Song(title, artist, duration);
    if (head == null) {
      head = newSong;
      head.next = head;
      current = head;
    } else {
      Song temp = head;
      while (temp.next != head) {
        temp = temp.next;
      }
      temp.next = newSong;
      newSong.next = head;
    }
  }

  public void removeSong(String title) {
    if (head == null)
      return;
    Song temp = head, prev = null;
    do {
      if (temp.title.equalsIgnoreCase(title)) {
        if (prev == null) { // head to be removed
          if (head.next == head) {
            head = null;
            current = null;
            return;
          }
          Song last = head;
          while (last.next != head)
            last = last.next;
          head = head.next;
          last.next = head;
          if (current == temp)
            current = head;
        } else {
          prev.next = temp.next;
          if (current == temp)
            current = temp.next;
        }
        return;
      }
      prev = temp;
      temp = temp.next;
    } while (temp != head);
  }

  public void playNext() {
    if (current != null)
      current = current.next;
  }

  public void playPrevious() {
    if (current == null)
      return;
    Song temp = head, prev = null;
    do {
      if (temp.next == current) {
        prev = temp;
        break;
      }
      temp = temp.next;
    } while (temp != head);
    if (prev != null)
      current = prev;
  }

  public Song getCurrentSong() {
    return current;
  }

  public void shuffle() {
    if (head == null || head.next == head)
      return;
    List<Song> songs = new ArrayList<>();
    Song temp = head;
    do {
      songs.add(temp);
      temp = temp.next;
    } while (temp != head);
    Collections.shuffle(songs);
    for (int i = 0; i < songs.size(); i++) {
      songs.get(i).next = songs.get((i + 1) % songs.size());
    }
    head = songs.get(0);
    current = head;
  }

  public String getTotalDuration() {
    int total = 0;
    if (head == null)
      return "00:00";
    Song temp = head;
    do {
      total += temp.duration;
      temp = temp.next;
    } while (temp != head);
    int mm = total / 60;
    int ss = total % 60;
    return String.format("%d:%02d", mm, ss);
  }

  public void displayPlaylist() {
    if (head == null) {
      System.out.println("Playlist kosong.");
      return;
    }
    System.out.println("=== Current Playlist ===");
    Song temp = head;
    int idx = 1;
    do {
      if (temp == current) {
        System.out.printf("-> Currently Playing: %s - %s (%d:%02d)\n", temp.title, temp.artist, temp.duration / 60,
            temp.duration % 60);
      } else {
        System.out.printf("   %d. %s - %s (%d:%02d)\n", idx, temp.title, temp.artist, temp.duration / 60,
            temp.duration % 60);
      }
      temp = temp.next;
      idx++;
    } while (temp != head);
    System.out.println("Total Duration: " + getTotalDuration());
  }

  // Contoh penggunaan
  public static void main(String[] args) {
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
  }
}

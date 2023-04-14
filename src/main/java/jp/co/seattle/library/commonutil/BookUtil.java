package jp.co.seattle.library.commonutil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;

@Service
public class BookUtil {
	final static Logger logger = LoggerFactory.getLogger(BookUtil.class);
	private static final String REQUIRED_ERROR = "未入力の必須項目があります";
	private static final String ISBN_ERROR = "ISBNの桁数または半角数字が正しくありません";
	private static final String PUBLISHDATE_ERROR = "出版日は半角数字のYYYYMMDD形式で入力してください";
	//addの74の情報がここにくる

	/**
	 * 登録前のバリデーションチェック
	 *
	 * @param bookInfo 書籍情報
	 * @return errorList エラーメッセージのリスト
	 */
	public List<String> checkBookInfo(BookDetailsInfo bookInfo) {

		//TODO　各チェックNGの場合はエラーメッセージをリストに追加（タスク４）
		List<String> errorList = new ArrayList<>();
		// 必須チェック
		if (isEmptyBookInfo(bookInfo) == false) {
			//89行メソッドの呼び出し、！で反転
			errorList.add(REQUIRED_ERROR);
		}

		// ISBNのバリデーションチェック
		//他のとこで似た書き方を見つける
		if (isValidIsbn(bookInfo.getIsbn()) == false) {
			errorList.add(ISBN_ERROR);
		}

		// 出版日の形式チェック  
		if (checkDate(bookInfo.getPublishDate()) == false) {
			errorList.add(PUBLISHDATE_ERROR);
		}

		return errorList;
	}

	/**
	 * 日付の形式が正しいかどうか
	 * 
	 * @param publishDate
	 * @return
	 */
	private static boolean checkDate(String publishDate) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			formatter.setLenient(false); // ←これで厳密にチェックしてくれるようになる
			//TODO　取得した日付の形式が正しければtrue（タスク４）
			formatter.parse(publishDate);
			return true;
		} catch (Exception p) {
			p.printStackTrace();
			return false;
		}
	}

	/**
	 * ISBNの形式チェック
	 * 
	 * @param isbn
	 * @return ISBNが半角数字で10文字か13文字かどうか
	 */
	private static boolean isValidIsbn(String isbn) {
		//isValidIsbnがメソッド名、34行で呼び出される
		if (isbn.length() > 0) {
			if (isbn.matches("^[0-9]{10}||{13}+$")) {
				return true;
			}
			return false;
		}
		return true;
		//0-9が半角数字、かつ、10桁か13桁
	}

	/**
	 * 必須項目の存在チェック
	 * 
	 * @param bookInfo
	 * @return タイトル、著者、出版社、出版日のどれか一つでもなかったらtrue
	 */
	private static boolean isEmptyBookInfo(BookDetailsInfo bookInfo) {
		//TODO　タイトル、著者、出版社、出版日のどれか一つでもなかったらtrue（タスク４）
		//if((!bookInfo.getTitle().isEmpty())){メソッドを入れなきゃいけないから違う方法模索
		if (bookInfo.getTitle().length() != 0 && bookInfo.getAuthor().length() != 0
				&& bookInfo.getPublisher().length() != 0 && bookInfo.getPublishDate().length() != 0) {
			return true;
		}
		return false;
	}
}

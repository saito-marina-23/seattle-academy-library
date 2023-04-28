package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.service.UsersService;

/**
 * アカウント作成コントローラー
 */
@Controller // APIの入り口
public class PasswordResetController {
	final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UsersService usersService;
	//Userserviceの処理をこのクラス内で自由に呼び出せるアノテーション、newより保守性が高い

	@RequestMapping(value = "/resetPasseword", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	public String resetPassword(Model model) {

		//newAccountにアクセスしたらアカウント作成画面に遷移しますよ。
		return "resetPassword";
	}

	/**
	 * 新規アカウント作成
	 *
	 * @param email            メールアドレス
	 * @param password         パスワード
	 * @param passwordForCheck 確認用パスワード
	 * @param model
	 * @return ホーム画面に遷移
	 */
	@Transactional
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public String resetPassword(Locale locale, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("passwordForCheck") String passwordForCheck,
			Model model) {
		// デバッグ用ログ
		logger.info("Welcome createAccount! The client locale is {}.", locale);

		// バリデーションチェック、パスワード一致チェック（タスク１）

		if (password.length() >= 8 && password.matches("^[0-9a-zA-Z]+$")) {
			if (password.equals(passwordForCheck)) {
				// パラメータで受け取ったアカウント情報をDtoに格納する。
				UserInfo userInfo = new UserInfo();
				userInfo.setEmail(email);
				userInfo.setPassword(password);
				usersService.updatePassword(userInfo);
				return "redirect:/";
			} else {
				model.addAttribute("errorMessage", "パスワードが一致しません。");
				return "createAccount";
			}
		} else {
			model.addAttribute("errorMessage", "パスワードは8文字以上かつ半角英数字に設定してください");
			return "createAccount";
		}

	}

}

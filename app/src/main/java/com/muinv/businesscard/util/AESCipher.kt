package com.magro.myapplicationaes

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


//
// AESサイファーオブジェクト
//
object AESCipher {
    //  PHP 暗号化方式
    //  $method   = 'aes-128-cbc';
    //  $options  = OPENSSL_RAW_DATA;
    //　上記の設定に準拠
    private val CIPHER_TRANSFORM = "AES/CBC/PKCS5PADDING"
    private val CIPHER_KEY_LEN = 16 //128 bits

    val key:String = "abcdefghijklmnop"
    val iv:String  = "1234567890123456"

    // 暗号化クラス
    // key  : 暗号化キー
    // iv   : 初期化ベクトルキー
    // data : 変換文字列
    // 戻り値: AES暗号化（base64）文字列
    fun encryption(key: String, iv: String, data: String): String? {

        try {
            // iv インスタンス
            val initVector = IvParameterSpec(iv.toByteArray(charset("UTF-8")))
            // aes key インスタンス
            val keySpec = SecretKeySpec(key.toByteArray(), "AES")
            // cipher(暗号) インスタンス
            val cipher = Cipher.getInstance(CIPHER_TRANSFORM)
            // 初期化：モード設定・キー・初期化ベクトル
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, initVector)

            // AES 暗号化
            val aesencode = cipher.doFinal(data.toByteArray(charset("UTF-8")))
            // Base64コード変換
            val base64EdcodeData = Base64.encode(aesencode, Base64.DEFAULT)

            // 暗号化文字列（String）リターン
            return String(base64EdcodeData)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }



    // 複合クラス
    // key  : 複合キー
    // iv   : 初期化ベクトルキー
    // data : AES暗号化（base64）文字列
    // 戻り値: 複合文字列
    fun decrypt(key: String, initVector: String, data: String): String? {

        try {
            // iv インスタンス
            val iv = IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
            // aes key インスタンス
            val keySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")
            // cipher(暗号) インスタンス
            val cipher = Cipher.getInstance(CIPHER_TRANSFORM)
            // 初期化：モード設定・キー・初期化ベクトル
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)

            // Base64コード複合
            val base64DecodeData = Base64.decode(data, Base64.DEFAULT)
            // AES 複合
            val decodeData = cipher.doFinal(base64DecodeData)

            // 複合文字列（String）リターン
            return String(decodeData)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }



}

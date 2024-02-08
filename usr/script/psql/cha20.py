from Crypto.Cipher import ChaCha20
import os

# 加密密钥，需要与解密时相同
key = b'this_is_a_32_byte_long_secret_key!!'

# 初始化加密器
cipher = ChaCha20.new(key=key)

# 打开待加密文件和输出文件
with open('plaintext.txt', 'rb') as f_in, open('ciphertext.bin', 'wb') as f_out:
    # 生成随机的初始向量，并写入输出文件头部
    iv = os.urandom(ChaCha20.block_size)
    f_out.write(iv)
    
    # 生成加密流，并写入输出文件
    stream = cipher.encrypt(iv)
    f_out.write(stream)
    
    # 分块读入待加密文件，并使用加密流进行加密
    while True:
        plaintext = f_in.read(1024)
        if len(plaintext) == 0:
            break
        ciphertext = stream.encrypt(plaintext)
        f_out.write(ciphertext)

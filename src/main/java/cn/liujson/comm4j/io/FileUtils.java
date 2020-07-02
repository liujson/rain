package cn.liujson.comm4j.io;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * description
 *
 * @author Liujs
 * @date 2019/9/18
 */
public class FileUtils {

    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;

    /**
     * The number of bytes in a kilobyte.
     */
    public static final BigInteger ONE_KB_BI = BigInteger.valueOf(ONE_KB);

    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;

    /**
     * The number of bytes in a megabyte.
     */
    public static final BigInteger ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI);

    /**
     * The number of bytes in a gigabyte.
     */
    public static final long ONE_GB = ONE_KB * ONE_MB;

    /**
     * The number of bytes in a gigabyte.
     */
    public static final BigInteger ONE_GB_BI = ONE_KB_BI.multiply(ONE_MB_BI);

    /**
     * The number of bytes in a terabyte.
     */
    public static final long ONE_TB = ONE_KB * ONE_GB;

    /**
     * The number of bytes in a terabyte.
     */
    public static final BigInteger ONE_TB_BI = ONE_KB_BI.multiply(ONE_GB_BI);

    /**
     * The number of bytes in a petabyte.
     */
    public static final long ONE_PB = ONE_KB * ONE_TB;

    /**
     * The number of bytes in a petabyte.
     */
    public static final BigInteger ONE_PB_BI = ONE_KB_BI.multiply(ONE_TB_BI);

    /**
     * The number of bytes in an exabyte.
     */
    public static final long ONE_EB = ONE_KB * ONE_PB;

    /**
     * The number of bytes in an exabyte.
     */
    public static final BigInteger ONE_EB_BI = ONE_KB_BI.multiply(ONE_PB_BI);

//    /**
//     * The number of bytes in a zettabyte.
//     */
//    public static final BigInteger ONE_ZB = BigInteger.valueOf(ONE_KB).multiply(BigInteger.valueOf(ONE_EB));
//
//    /**
//     * The number of bytes in a yottabyte.
//     */
//    public static final BigInteger ONE_YB = ONE_KB_BI.multiply(ONE_ZB);


    /**
     * 获取系统临时目录
     *
     * @return 系统临时目录
     */
    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }
    /**
     * Returns a {@link File} representing the system temporary directory.
     *
     * @return the system temporary directory.
     */
    public static File getTempDirectory() {
        return new File(getTempDirectoryPath());
    }
    /**
     * 获取用户主目录的路径
     * Returns the path to the user's home directory.
     *
     * @return the path to the user's home directory.
     */
    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }
    /**
     * Returns a {@link File} representing the user's home directory.
     *
     * @return the user's home directory.
     */
    public static File getUserDirectory() {
        return new File(getUserDirectoryPath());
    }

    /**
     * 打开文件的输入流
     * 在方法结束时，要么成功打开流，要么抛出异常。
     * @param file the file to open for input, must not be {@code null}
     * @return a new {@link FileInputStream} for the specified file
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException if the file object is a directory
     * @throws IOException if the file cannot be read
     */
    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    /**
     * 打开文件的输出流
     * 在方法结束时，要么成功打开流，要么抛出异常。
     * @param file  the file to open for output, must not be {@code null}
     * @return a new {@link FileOutputStream} for the specified file
     * @throws IOException if the file object is a directory
     * @throws IOException if the file cannot be written to
     * @throws IOException if a parent directory needs creating but that fails
     */
    public static FileOutputStream openOutputStream(File file) throws IOException {
        return openOutputStream(file, false);
    }

    /**
     * 打开文件的输入流
     * 在方法结束时，要么成功打开流，要么抛出异常。
     * 如果目录不存在，则创建它。
     * @param file  the file to open for output, must not be {@code null}
     * @param append if {@code true}, then bytes will be added to the
     * end of the file rather than overwriting
     * @return a new {@link FileOutputStream} for the specified file
     * @throws IOException if the file object is a directory
     * @throws IOException if the file cannot be written to
     * @throws IOException if a parent directory needs creating but that fails
     */
    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }

    /**
     * 计算并返回返回人类可读的文件大小
     * 如果大小超过1GB，则返回大小作为整个GB的数量，即大小四舍五入到最近的GB边界。
     * @param size 文件的字节数
     * @return 人可读的显示值(包括单位- EB、PB、TB、GB、MB、KB或bytes)
     */
    public static String byteCountToDisplaySize(BigInteger size) {
        String displaySize;

        if (size.divide(ONE_EB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_EB_BI)) + " EB";
        } else if (size.divide(ONE_PB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_PB_BI)) + " PB";
        } else if (size.divide(ONE_TB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_TB_BI)) + " TB";
        } else if (size.divide(ONE_GB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_GB_BI)) + " GB";
        } else if (size.divide(ONE_MB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_MB_BI)) + " MB";
        } else if (size.divide(ONE_KB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_KB_BI)) + " KB";
        } else {
            displaySize = String.valueOf(size) + " bytes";
        }
        return displaySize;
    }

    /**
     * 计算并返回返回人类可读的文件大小
     * @param size 文件的字节数
     * @return 人可读的显示值(包括单位- EB、PB、TB、GB、MB、KB或bytes)
     */
    public static String byteCountToDisplaySize(long size) {
        return byteCountToDisplaySize(BigInteger.valueOf(size));
    }

    /**
     * 创建一个大小为 0 的新文件，如果文件已经存在，则打开它并
     * 关闭不修改，但更新文件日期和时间。
     * @param file the File to touch
     * @throws IOException If an I/O problem occurs
     */
    public static void touch(File file) throws IOException {
        if (!file.exists()) {
            OutputStream out = openOutputStream(file);
            IOUtils.closeQuietly(out);
        }
        boolean success = file.setLastModified(System.currentTimeMillis());
        if (!success) {
            throw new IOException("Unable to set the last modification time for " + file);
        }
    }

    /**
     * 拷贝文件 到另一个文件
     * CopyOption 实现类 {@link StandardCopyOption}
     *
     * @param srcFile 源文件
     * @param destFile 目标文件
     * @throws IOException
     */
    public static void copyFile(File srcFile, File destFile, CopyOption... copyOptions) throws IOException {
        Files.copy(srcFile.toPath(),destFile.toPath(),copyOptions);
    }

    /**
     * 拷贝文件到输出流
     * @param input 源文件
     * @param output 输出流
     * @return 读取或写入的字节数
     * @throws IOException
     */
    public static long copyFile(File input, OutputStream output) throws IOException {
       return Files.copy(input.toPath(),output);
    }

    /**
     *  此方法复制指定源文件的内容
     * 指向指定目标目录中同名的文件。
     * 如果目标目录不存在，则创建该目录。
     * 如果目标文件存在，则此方法将覆盖它。
     * @param srcFile  an existing file to copy, must not be {@code null}
     * @param destDir the directory to place the copy in, must not be {@code null}
     * @throws NullPointerException if source or destination is null
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     */
    public static void copyFileToDirectory(File srcFile, File destDir) throws IOException {
        copyFileToDirectory(srcFile, destDir, StandardCopyOption.REPLACE_EXISTING);
    }
    /**
     * 拷贝文件到目录
     * @param srcFile  an existing file to copy, must not be {@code null}
     * @param destDir the directory to place the copy in, must not be {@code null}
     * @throws NullPointerException if source or destination is null
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     */
    public static void copyFileToDirectory(File srcFile, File destDir,CopyOption... copyOptions) throws IOException {
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (destDir.exists() && destDir.isDirectory() == false) {
            throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
        }
        File destFile = new File(destDir, srcFile.getName());
        copyFile(srcFile, destFile, copyOptions);
    }


    /**
     * 将流写到文件中，如果文件不存在将被创建，如果存在，将被覆盖
     * @param source  the <code>InputStream</code> to copy bytes from, must not be {@code null}
     * @param destination  the non-directory <code>File</code> to write bytes to
     *  (possibly overwriting), must not be {@code null}
     * @throws IOException if <code>destination</code> is a directory
     * @throws IOException if <code>destination</code> cannot be written
     * @throws IOException if <code>destination</code> needs creating but can't be
     * @throws IOException if an IO error occurs during copying
     */
    public static void copyInputStreamToFile(InputStream source, File destination) throws IOException {
        try {
            FileOutputStream output = openOutputStream(destination);
            try {
                IOUtils.copy(source, output);
                output.close(); // don't swallow close Exception if copy completes normally
            } finally {
                IOUtils.closeQuietly(output);
            }
        } finally {
            IOUtils.closeQuietly(source);
        }
    }


    //-----------------------------------------------------------------------
    /**
     * 递归删除目录。
     * Deletes a directory recursively.
     *
     * @param directory  directory to delete
     * @throws IOException in case deletion is unsuccessful
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        if (!isSymlink(directory)) {
            cleanDirectory(directory);
        }

        if (!directory.delete()) {
            String message ="Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    /**
     * 删除文件，从不引发异常。如果文件是一个目录，删除它和所有子目录。
     * Deletes a file, never throwing an exception. If file is a directory, delete it and all sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>No exceptions are thrown when a file or directory cannot be deleted.</li>
     * </ul>
     *
     * @param file  file or directory to delete, can be {@code null}
     * @return {@code true} if the file or directory was deleted, otherwise
     * {@code false}
     *
     * @since 1.4
     */
    public static boolean deleteQuietly(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (Exception ignored) {
        }

        try {
            return file.delete();
        } catch (Exception ignored) {
            return false;
        }
    }
    /**
     * Deletes a file. If file is a directory, delete it and all sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>You get exceptions when a file or directory cannot be deleted.
     *      (java.io.File methods returns a boolean)</li>
     * </ul>
     *
     * @param file  file or directory to delete, must not be {@code null}
     * @throws NullPointerException if the directory is {@code null}
     * @throws FileNotFoundException if the file was not found
     * @throws IOException in case deletion is unsuccessful
     */
    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {

            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent){
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                String message =
                        "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }
    /**
     * Cleans a directory without deleting it.
     *
     * @param directory directory to clean
     * @throws IOException in case cleaning is unsuccessful
     */
    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        // null if security restricted
        if (files == null) {
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (File file : files) {
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    /**
     * Reads the contents of a file into a String.
     * The file is always closed.
     *
     * @param file  the file to read, must not be {@code null}
     * @param encoding  the encoding to use, {@code null} means platform default
     * @return the file contents, never {@code null}
     * @throws IOException in case of an I/O error
     * @since 2.3
     */
    public static String readFileToString(File file, Charset encoding) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return IOUtils.toString(in, encoding);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static String readFileToString(File file, String encoding) throws IOException {
        return readFileToString(file, Charset.forName(encoding));
    }

    public static String readFileToString(File file) throws IOException {
        return readFileToString(file, Charset.defaultCharset());
    }

    /**
     * Reads the contents of a file into a byte array.
     * The file is always closed.
     *
     * @param file  the file to read, must not be {@code null}
     * @return the file contents, never {@code null}
     * @throws IOException in case of an I/O error
     * @since 1.1
     */
    public static byte[] readFileToByteArray(File file) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return IOUtils.toByteArray(in, file.length());
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * Reads the contents of a file line by line to a List of Strings.
     * The file is always closed.
     *
     * @param file  the file to read, must not be {@code null}
     * @param encoding  the encoding to use, {@code null} means platform default
     * @return the list of Strings representing each line in the file, never {@code null}
     * @throws IOException in case of an I/O error
     * @since 2.3
     */
    public static List<String> readLines(File file, Charset encoding) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return IOUtils.readLines(in, encoding);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
    /**
     * Reads the contents of a file line by line to a List of Strings. The file is always closed.
     *
     * @param file
     *            the file to read, must not be {@code null}
     * @param encoding
     *            the encoding to use, {@code null} means platform default
     * @return the list of Strings representing each line in the file, never {@code null}
     * @throws IOException
     *             in case of an I/O error
     * @throws UnsupportedCharsetException
     *             thrown instead of {@link UnsupportedEncodingException} in version 2.2 if the encoding is not
     *             supported.
     * @since 1.1
     */
    public static List<String> readLines(File file, String encoding) throws IOException {
        return readLines(file, Charset.forName(encoding));
    }

    /**
     * Reads the contents of a file line by line to a List of Strings using the default encoding for the VM.
     * The file is always closed.
     *
     * @param file  the file to read, must not be {@code null}
     * @return the list of Strings representing each line in the file, never {@code null}
     * @throws IOException in case of an I/O error
     * @since 1.3
     */
    public static List<String> readLines(File file) throws IOException {
        return readLines(file, Charset.defaultCharset());
    }
    //-----------------------------------------------------------------------
    /**
     * Writes a String to a file creating the file if it does not exist.
     *
     * NOTE: As from v1.3, the parent directories of the file will be created
     * if they do not exist.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @param encoding  the encoding to use, {@code null} means platform default
     * @throws IOException in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 2.4
     */
    public static void writeStringToFile(File file, String data, Charset encoding) throws IOException {
        writeStringToFile(file, data, encoding, false);
    }
    /**
     * Writes a String to a file creating the file if it does not exist.
     *
     * NOTE: As from v1.3, the parent directories of the file will be created
     * if they do not exist.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @param encoding  the encoding to use, {@code null} means platform default
     * @throws IOException in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by the VM
     */
    public static void writeStringToFile(File file, String data, String encoding) throws IOException {
        writeStringToFile(file, data, encoding, false);
    }
    /**
     * 将字符串写入文件，如果文件不存在，则创建该文件。
     * Writes a String to a file creating the file if it does not exist.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @param encoding  the encoding to use, {@code null} means platform default
     * @param append if {@code true}, then the String will be added to the
     * end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @since 2.3
     */
    public static void writeStringToFile(File file, String data, Charset encoding, boolean append) throws IOException {
        OutputStream out = null;
        try {
            out = openOutputStream(file, append);
            IOUtils.write(data, out, encoding);
            out.close(); // don't swallow close Exception if copy completes normally
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    /**
     * Writes a String to a file creating the file if it does not exist.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @param encoding  the encoding to use, {@code null} means platform default
     * @param append if {@code true}, then the String will be added to the
     * end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @throws UnsupportedCharsetException
     *             thrown instead of {@link UnsupportedEncodingException} in version 2.2 if the encoding is not
     *             supported by the VM
     * @since 2.1
     */
    public static void writeStringToFile(File file, String data, String encoding, boolean append) throws IOException {
        writeStringToFile(file, data, Charset.forName(encoding), append);
    }
    /**
     * Writes a String to a file creating the file if it does not exist using the default encoding for the VM.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @throws IOException in case of an I/O error
     */
    public static void writeStringToFile(File file, String data) throws IOException {
        writeStringToFile(file, data, Charset.defaultCharset(), false);
    }

    /**
     * Writes a String to a file creating the file if it does not exist using the default encoding for the VM.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @param append if {@code true}, then the String will be added to the
     * end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @since 2.1
     */
    public static void writeStringToFile(File file, String data, boolean append) throws IOException {
        writeStringToFile(file, data, Charset.defaultCharset(), append);
    }
    /**
     * Writes a CharSequence to a file creating the file if it does not exist using the default encoding for the VM.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @throws IOException in case of an I/O error
     * @since 2.0
     */
    public static void write(File file, CharSequence data) throws IOException {
        write(file, data, Charset.defaultCharset(), false);
    }
    /**
     * Writes a CharSequence to a file creating the file if it does not exist using the default encoding for the VM.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @param append if {@code true}, then the data will be added to the
     * end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @since 2.1
     */
    public static void write(File file, CharSequence data, boolean append) throws IOException {
        write(file, data, Charset.defaultCharset(), append);
    }
    /**
     * Writes a CharSequence to a file creating the file if it does not exist.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @param encoding  the encoding to use, {@code null} means platform default
     * @throws IOException in case of an I/O error
     * @since 2.3
     */
    public static void write(File file, CharSequence data, Charset encoding) throws IOException {
        write(file, data, encoding, false);
    }
    /**
     * Writes a CharSequence to a file creating the file if it does not exist.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @param encoding  the encoding to use, {@code null} means platform default
     * @throws IOException in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 2.0
     */
    public static void write(File file, CharSequence data, String encoding) throws IOException {
        write(file, data, encoding, false);
    }
    /**
     * Writes a CharSequence to a file creating the file if it does not exist.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @param encoding  the encoding to use, {@code null} means platform default
     * @param append if {@code true}, then the data will be added to the
     * end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @since 2.3
     */
    public static void write(File file, CharSequence data, Charset encoding, boolean append) throws IOException {
        String str = data == null ? null : data.toString();
        writeStringToFile(file, str, encoding, append);
    }
    /**
     * Writes a CharSequence to a file creating the file if it does not exist.
     *
     * @param file  the file to write
     * @param data  the content to write to the file
     * @param encoding  the encoding to use, {@code null} means platform default
     * @param append if {@code true}, then the data will be added to the
     * end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @throws UnsupportedCharsetException
     *             thrown instead of {@link UnsupportedEncodingException} in version 2.2 if the encoding is not
     *             supported by the VM
     * @since IO 2.1
     */
    public static void write(File file, CharSequence data, String encoding, boolean append) throws IOException {
        write(file, data, Charset.forName(encoding), append);
    }
    /**
     * Writes a byte array to a file creating the file if it does not exist.
     * <p>
     * NOTE: As from v1.3, the parent directories of the file will be created
     * if they do not exist.
     *
     * @param file  the file to write to
     * @param data  the content to write to the file
     * @throws IOException in case of an I/O error
     * @since 1.1
     */
    public static void writeByteArrayToFile(File file, byte[] data) throws IOException {
        writeByteArrayToFile(file, data, false);
    }
    /**
     * Writes a byte array to a file creating the file if it does not exist.
     *
     * @param file  the file to write to
     * @param data  the content to write to the file
     * @param append if {@code true}, then bytes will be added to the
     * end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @since IO 2.1
     */
    public static void writeByteArrayToFile(File file, byte[] data, boolean append) throws IOException {
        OutputStream out = null;
        try {
            out = openOutputStream(file, append);
            out.write(data);
            out.close(); // don't swallow close Exception if copy completes normally
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    /**
     * Writes the <code>toString()</code> value of each item in a collection to
     * the specified <code>File</code> line by line.
     * The specified character encoding and the default line ending will be used.
     * <p>
     * NOTE: As from v1.3, the parent directories of the file will be created
     * if they do not exist.
     *
     * @param file  the file to write to
     * @param encoding  the encoding to use, {@code null} means platform default
     * @param lines  the lines to write, {@code null} entries produce blank lines
     * @throws IOException in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 1.1
     */
    public static void writeLines(File file, String encoding, Collection<?> lines) throws IOException {
        writeLines(file, encoding, lines, null, false);
    }
    /**
     * Writes the <code>toString()</code> value of each item in a collection to
     * the specified <code>File</code> line by line, optionally appending.
     * The specified character encoding and the default line ending will be used.
     *
     * @param file  the file to write to
     * @param encoding  the encoding to use, {@code null} means platform default
     * @param lines  the lines to write, {@code null} entries produce blank lines
     * @param append if {@code true}, then the lines will be added to the
     * end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 2.1
     */
    public static void writeLines(File file, String encoding, Collection<?> lines, boolean append) throws IOException {
        writeLines(file, encoding, lines, null, append);
    }
    /**
     * Writes the <code>toString()</code> value of each item in a collection to
     * the specified <code>File</code> line by line.
     * The default VM encoding and the default line ending will be used.
     *
     * @param file  the file to write to
     * @param lines  the lines to write, {@code null} entries produce blank lines
     * @throws IOException in case of an I/O error
     * @since 1.3
     */
    public static void writeLines(File file, Collection<?> lines) throws IOException {
        writeLines(file, null, lines, null, false);
    }
    /**
     * Writes the <code>toString()</code> value of each item in a collection to
     * the specified <code>File</code> line by line.
     * The default VM encoding and the default line ending will be used.
     *
     * @param file  the file to write to
     * @param lines  the lines to write, {@code null} entries produce blank lines
     * @param append if {@code true}, then the lines will be added to the
     * end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @since 2.1
     */
    public static void writeLines(File file, Collection<?> lines, boolean append) throws IOException {
        writeLines(file, null, lines, null, append);
    }
    /**
     * Writes the <code>toString()</code> value of each item in a collection to
     * the specified <code>File</code> line by line.
     * The specified character encoding and the line ending will be used.
     * <p>
     * NOTE: As from v1.3, the parent directories of the file will be created
     * if they do not exist.
     *
     * @param file  the file to write to
     * @param encoding  the encoding to use, {@code null} means platform default
     * @param lines  the lines to write, {@code null} entries produce blank lines
     * @param lineEnding  the line separator to use, {@code null} is system default
     * @throws IOException in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 1.1
     */
    public static void writeLines(File file, String encoding, Collection<?> lines, String lineEnding)
            throws IOException {
        writeLines(file, encoding, lines, lineEnding, false);
    }
    /**
     * Writes the <code>toString()</code> value of each item in a collection to
     * the specified <code>File</code> line by line.
     * The specified character encoding and the line ending will be used.
     *
     * @param file  the file to write to
     * @param encoding  the encoding to use, {@code null} means platform default
     * @param lines  the lines to write, {@code null} entries produce blank lines
     * @param lineEnding  the line separator to use, {@code null} is system default
     * @param append if {@code true}, then the lines will be added to the
     * end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 2.1
     */
    public static void writeLines(File file, String encoding, Collection<?> lines, String lineEnding, boolean append)
            throws IOException {
        FileOutputStream out = null;
        try {
            out = openOutputStream(file, append);
            final BufferedOutputStream buffer = new BufferedOutputStream(out);
            IOUtils.writeLines(lines, lineEnding, buffer, encoding);
            buffer.flush();
            out.close(); // don't swallow close Exception if copy completes normally
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    /**
     * Writes the <code>toString()</code> value of each item in a collection to
     * the specified <code>File</code> line by line.
     * The default VM encoding and the specified line ending will be used.
     *
     * @param file  the file to write to
     * @param lines  the lines to write, {@code null} entries produce blank lines
     * @param lineEnding  the line separator to use, {@code null} is system default
     * @throws IOException in case of an I/O error
     * @since 1.3
     */
    public static void writeLines(File file, Collection<?> lines, String lineEnding) throws IOException {
        writeLines(file, null, lines, lineEnding, false);
    }

    /**
     * Writes the <code>toString()</code> value of each item in a collection to
     * the specified <code>File</code> line by line.
     * The default VM encoding and the specified line ending will be used.
     *
     * @param file  the file to write to
     * @param lines  the lines to write, {@code null} entries produce blank lines
     * @param lineEnding  the line separator to use, {@code null} is system default
     * @param append if {@code true}, then the lines will be added to the
     * end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @since 2.1
     */
    public static void writeLines(File file, Collection<?> lines, String lineEnding, boolean append)
            throws IOException {
        writeLines(file, null, lines, lineEnding, append);
    }

    /**
     * Schedules a file to be deleted when JVM exits.
     * If file is directory delete it and all sub-directories.
     *
     * @param file  file or directory to delete, must not be {@code null}
     * @throws NullPointerException if the file is {@code null}
     * @throws IOException in case deletion is unsuccessful
     */
    public static void forceDeleteOnExit(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectoryOnExit(file);
        } else {
            file.deleteOnExit();
        }
    }

    /**
     * Schedules a directory recursively for deletion on JVM exit.
     *
     * @param directory  directory to delete, must not be {@code null}
     * @throws NullPointerException if the directory is {@code null}
     * @throws IOException in case deletion is unsuccessful
     */
    private static void deleteDirectoryOnExit(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        directory.deleteOnExit();
        if (!isSymlink(directory)) {
            cleanDirectoryOnExit(directory);
        }
    }

    /**
     * Cleans a directory without deleting it.
     *
     * @param directory  directory to clean, must not be {@code null}
     * @throws NullPointerException if the directory is {@code null}
     * @throws IOException in case cleaning is unsuccessful
     */
    private static void cleanDirectoryOnExit(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (File file : files) {
            try {
                forceDeleteOnExit(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    /**
     * Makes a directory, including any necessary but nonexistent parent
     * directories. If a file already exists with specified name but it is
     * not a directory then an IOException is thrown.
     * If the directory cannot be created (or does not already exist)
     * then an IOException is thrown.
     *
     * @param directory  directory to create, must not be {@code null}
     * @throws NullPointerException if the directory is {@code null}
     * @throws IOException if the directory cannot be created or the file already exists but is not a directory
     */
    public static void forceMkdir(File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                String message =
                        "File "
                                + directory
                                + " exists and is "
                                + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else {
            if (!directory.mkdirs()) {
                // Double-check that some other thread or process hasn't made
                // the directory in the background
                if (!directory.isDirectory())
                {
                    String message =
                            "Unable to create directory " + directory;
                    throw new IOException(message);
                }
            }
        }
    }
    //-----------------------------------------------------------------------
    /**
     * Returns the size of the specified file or directory. If the provided
     * {@link File} is a regular file, then the file's length is returned.
     * If the argument is a directory, then the size of the directory is
     * calculated recursively. If a directory or subdirectory is security
     * restricted, its size will not be included.
     *
     * @param file the regular file or directory to return the size
     *        of (must not be {@code null}).
     *
     * @return the length of the file, or recursive size of the directory,
     *         provided (in bytes).
     *
     * @throws NullPointerException if the file is {@code null}
     * @throws IllegalArgumentException if the file does not exist.
     *
     * @since 2.0
     */
    public static long sizeOf(File file) {

        if (!file.exists()) {
            String message = file + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (file.isDirectory()) {
            return sizeOfDirectory(file);
        } else {
            return file.length();
        }

    }

    /**
     * Returns the size of the specified file or directory. If the provided
     * {@link File} is a regular file, then the file's length is returned.
     * If the argument is a directory, then the size of the directory is
     * calculated recursively. If a directory or subdirectory is security
     * restricted, its size will not be included.
     *
     * @param file the regular file or directory to return the size
     *        of (must not be {@code null}).
     *
     * @return the length of the file, or recursive size of the directory,
     *         provided (in bytes).
     *
     * @throws NullPointerException if the file is {@code null}
     * @throws IllegalArgumentException if the file does not exist.
     *
     * @since 2.4
     */
    public static BigInteger sizeOfAsBigInteger(File file) {

        if (!file.exists()) {
            String message = file + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (file.isDirectory()) {
            return sizeOfDirectoryAsBigInteger(file);
        } else {
            return BigInteger.valueOf(file.length());
        }

    }

    /**
     * Checks that the given {@code File} exists and is a directory.
     *
     * @param directory The {@code File} to check.
     * @throws IllegalArgumentException if the given {@code File} does not exist or is not a directory.
     */
    private static void checkDirectory(File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory + " is not a directory");
        }
    }

    /**
     * Counts the size of a directory recursively (sum of the length of all files).
     *
     * @param directory
     *            directory to inspect, must not be {@code null}
     * @return size of directory in bytes, 0 if directory is security restricted, a negative number when the real total
     *         is greater than {@link Long#MAX_VALUE}.
     * @throws NullPointerException
     *             if the directory is {@code null}
     */
    public static long sizeOfDirectory(File directory) {
        checkDirectory(directory);

        final File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            return 0L;
        }
        long size = 0;

        for (final File file : files) {
            try {
                if (!isSymlink(file)) {
                    size += sizeOf(file);
                    if (size < 0) {
                        break;
                    }
                }
            } catch (IOException ioe) {
                // Ignore exceptions caught when asking if a File is a symlink.
            }
        }

        return size;
    }

    /**
     * Counts the size of a directory recursively (sum of the length of all files).
     *
     * @param directory
     *            directory to inspect, must not be {@code null}
     * @return size of directory in bytes, 0 if directory is security restricted.
     * @throws NullPointerException
     *             if the directory is {@code null}
     *  @since 2.4
     */
    public static BigInteger sizeOfDirectoryAsBigInteger(File directory) {
        checkDirectory(directory);

        final File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            return BigInteger.ZERO;
        }
        BigInteger size = BigInteger.ZERO;

        for (final File file : files) {
            try {
                if (!isSymlink(file)) {
                    size = size.add(BigInteger.valueOf(sizeOf(file)));
                }
            } catch (IOException ioe) {
                // Ignore exceptions caught when asking if a File is a symlink.
            }
        }

        return size;
    }

    /**
     * 判断文件是否是链接文件
     * Determines whether the specified file is a Symbolic Link rather than an actual file.
     * <p>
     * Will not return true if there is a Symbolic Link anywhere in the path,
     * only if the specific file is.
     * <p>
     * <b>Note:</b> the current implementation always returns {@code false} if the system
     * is detected as Windows using {@link this#isSystemWindows()}
     *
     * @param file the file to check
     * @return true if the file is a Symbolic Link
     * @throws IOException if an IO error occurs while checking the file
     * @since 2.0
     */
    public static boolean isSymlink(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        if (isSystemWindows()) {
            return false;
        }
        File fileInCanonicalDir = null;
        if (file.getParent() == null) {
            fileInCanonicalDir = file;
        } else {
            File canonicalDir = file.getParentFile().getCanonicalFile();
            fileInCanonicalDir = new File(canonicalDir, file.getName());
        }

        if (Objects.equals(fileInCanonicalDir.getCanonicalFile(), fileInCanonicalDir.getAbsoluteFile())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * The Windows separator character.
     */
    private static final char WINDOWS_SEPARATOR = '\\';

    private static boolean isSystemWindows() {
        return File.separatorChar == WINDOWS_SEPARATOR;
    }


    //------------------------------------------------------
    /**
     * 获取文件后缀名
     * @param file 文件
     * @return 文件后缀
     */
    public static String getFileSuffix(File file){
        if(file == null){
            throw new NullPointerException("File must not be null");
        }
        return getFileSuffix(file.getName());
    }

    /**
     * 获取文件后缀名
     *
     * abc.d.txt    -->  "txt"
     * abc.txt      -->  "txt"
     * cde.a        -->  "a"
     * @param fileName 文件名 ，不能为空
     * @return 后缀名称
     */
    public static String getFileSuffix(String fileName){
        if(fileName == null){
            throw new NullPointerException("Name must not be null");
        }
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
}

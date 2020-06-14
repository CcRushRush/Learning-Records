# Learning-Records
The Way of Growth
提交测试
## HashMap学习 
### hashmap原理：数组+链表  

### hashmap put放入元素的时候情况分析  
1. 当放入的位置没有元素的时候，直接给他存放
2. 存放的位置已经存在元素，即加入到该链表，当链表的长度 > 8时，链表 ==> 红黑树（jdk1.8后才有）
3. 如果存在的节点存在，替换旧值

### hashmap什么时候扩容，怎样扩容  
当hashmap内存已经使用了3/4的时候就开始扩容，内存扩大为原来的2倍，重新就算hash值，当扩容后，在哈希表扩容时，如果发现链表长度小于 6，则会由树重新退化为链表。

### hash冲突解决方法  
1. 开放地址法
如果两个数据元素的哈希值相同，则在哈希表中为后插入的数据元素另外选择一个表项。
当程序查找哈希表时，如果没有在第一个对应的哈希表项中找到符合查找要求的数据元素，程序就会继续往后查找，直到找到一个符合查找要求的数据元素，或者遇到一个空的表项。
  1. 线性探测法（结果可能会产生溢出,原因：解决冲突时，依次探测下一个地址，直到有空的地址后插入，若整个空间都找遍仍然找不到空余的地址）
  2. 二次探测法
  3. 双哈希函数探测法
2. 链地址法
将哈希值相同的数据元素存放在一个链表中，在查找哈希表的过程中，当查找到这个链表时，必须采用线性查找方法。  
可以参考：https://bestswifter.com/hashtable/

## 图片上传代码
### 第一种
~~~java
    //自己设定的文件保存路径
    public String imgFilePath ;
public String uploadImage(MultipartFile file) throws Exception{

        // 获取原始文件后
        String fileNameOld = file.getOriginalFilename();
        //截取后缀名
        String fileSuffix = fileNameOld.substring( fileNameOld.lastIndexOf("."), fileNameOld.length());

        // 生成新的文件名
        String fileNameNew = System.currentTimeMillis() + "_" +new Random().nextInt(1000) + fileSuffix;

        //获取文件夹路径
        String dataPath = DateUtil.DateTime2FormatStr(new Date(), DateUtil.DATE_FORMAT_CONCAT);
        File dateFile =new File(imgFilePath + "/" + dataPath);

        // 如果文件夹不存在则创建
        if(!dateFile .exists()  && !dateFile .isDirectory()){
            dateFile .mkdir();
        }

        // 将图片存入文件夹
        File targetFile = new File(dateFile, fileNameNew);

        //将上传的文件写到服务器上指定的文件。
        file.transferTo(targetFile);

        //返回上传的文件路径
        String returnUrl = dataPath + "/" + fileNameNew;
        return returnUrl ;
    }
~~~
### 第二种方法上传图片
~~~java
public String upload(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) 
			throws IllegalStateException, IOException {
		System.out.println("UploadController.upload()");
		
		// 判断上传的文件是否为空
		boolean isEmpty = file.isEmpty();
		System.out.println("\tisEmpty=" + isEmpty);
		if (isEmpty) {
			throw new RuntimeException("上传失败！上传的文件为空！");
		}
		
		// 检查文件大小
		long fileSize = file.getSize();
		System.out.println("\tsize=" + fileSize);
		if (fileSize > 1 * 1024 * 1024) {
			throw new RuntimeException("上传失败！上传的文件大小超出了限制！");
		}
		
		// 检查文件MIME类型
		String contentType = file.getContentType();
		System.out.println("\tcontentType=" + contentType);
		List<String> types = new ArrayList<String>();
		types.add("image/jpeg");
		types.add("image/png");
		types.add("image/gif");
		if (!types.contains(contentType)) {
			throw new RuntimeException("上传失败！不允许上传此类型的文件！");
		}
		
		// 准备文件夹,获取项目中upload文件夹的路径
		String parentDir = request.getServletContext().getRealPath("upload");
		// request.getSession().getServletContext().getRealPath("");
		// request.getRealPath("");
		System.out.println("\tpath=" + parentDir);
		File parent = new File(parentDir);
		if (!parent.exists()) {
			parent.mkdirs();
		}
		
		// 获取原始文件名
		String originalFilename = file.getOriginalFilename();
		System.out.println("\toriginalFilename=" + originalFilename);
		
		// 确定最终保存时使用的文件
		String filename = UUID.randomUUID().toString();
		String suffix = "";
		int beginIndex = originalFilename.lastIndexOf(".");
		if (beginIndex != -1) {
			suffix = originalFilename.substring(beginIndex);
		}
		
		// 执行保存文件
		File dest = new File(parent, filename + suffix);
		file.transferTo(dest);
		
		return "OK";
	}
~~~

###部分参考学习文档
 1. https://www.funtl.com/zh/docs-docker/
 2. https://www.funtl.com/zh/interview/
 3. https://www.funtl.com/zh/guide/

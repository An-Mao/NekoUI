package dev.anye.mc.nekoui;

import net.minecraft.world.entity.Entity;
import org.objectweb.asm.*;

import java.io.IOException;

public class EntityRenderStateAsm {
    public static final String className = "net.minecraft.client.renderer.entity.state.EntityRenderState"; // 替换为你的第三方类名
    public static final String fieldName = "cores$entity";         // 新增的字段名
    public static final String fieldType = "net/minecraft/world/entity/Entity"; // 新增的字段类型

    public static void Add() throws IOException {

        // 1. 读取字节码
        ClassReader classReader = new ClassReader(className);

        // 2. 创建 ClassWriter
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);

        // 3. 创建自定义 ClassVisitor
        ClassVisitor addFieldVisitor = new ClassVisitor(Opcodes.ASM9, classWriter) {
            @Override
            public void visitEnd() {
                // 在类定义结束时添加字段
                visitField(Opcodes.ACC_PUBLIC, fieldName, Type.getDescriptor(Entity.class), null, null).visitEnd();
                super.visitEnd();
            }
        };

        // 4. 连接
        classReader.accept(addFieldVisitor, 0);


        // 5. 输出修改后的字节码
        byte[] modifiedClassBytes = classWriter.toByteArray();
        /*
        // 将修改后的字节码写入文件 (可选，用于调试)
        try (FileOutputStream fos = new FileOutputStream(className + "Modified.class")) {
            fos.write(modifiedClassBytes);
        }

         */

        // 使用自定义类加载器加载修改后的类
        MyClassLoader classLoader = new MyClassLoader();
        Class<?> modifiedClass = classLoader.defineClass(className, modifiedClassBytes);

        /*
        //测试字段是否添加成功
        try {
            Object instance = modifiedClass.getDeclaredConstructor().newInstance();
            modifiedClass.getField(fieldName).set(instance, "Hello ASM!");
            System.out.println(modifiedClass.getField(fieldName).get(instance));
        }catch (Exception e){
            e.printStackTrace();
        }

         */
    }


    // 自定义类加载器
    static class MyClassLoader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }
}

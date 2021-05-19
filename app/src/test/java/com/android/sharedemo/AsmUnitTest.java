package com.android.sharedemo;

import org.junit.Test;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AsmUnitTest {
    @Test
    public void test() throws IOException {
        //获得待插桩的字节码数据
        FileInputStream fis = new FileInputStream("H:\\my_person_demo\\MyShareDemo\\app\\src\\test\\java\\com\\android\\sharedemo\\InjectTest.class");
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        //执行插桩，修改class数据
        ClassReader cr = new ClassReader(fis);
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM7, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                System.out.println("access = " + access + " name=" + name);
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MyMethodVisitor(Opcodes.ASM7, methodVisitor, access, name, descriptor);
            }

            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                return super.visitField(access, name, descriptor, signature, value);
            }
        };
        cr.accept(classVisitor, 0);

        //输出结果
        byte[] bytes = classWriter.toByteArray();
        File file = new File("H:\\my_person_demo\\MyShareDemo\\app\\src\\test\\java2\\com\\android\\sharedemo");
        if (!file.exists()) {
            file.mkdir();
        }
        FileOutputStream fos = new FileOutputStream("H:\\my_person_demo\\MyShareDemo\\app\\src\\test\\java2\\com\\android\\sharedemo\\InjectTest.class");
        fos.write(bytes);
        fos.close();
    }

    static class MyMethodVisitor extends AdviceAdapter {

        /**
         * Constructs a new {@link AdviceAdapter}.
         *
         * @param api           the ASM API version implemented by this visitor. Must be one of {@link
         *                      Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
         * @param methodVisitor the method visitor to which this adapter delegates calls.
         * @param access        the method's access flags (see {@link Opcodes}).
         * @param name          the method's name.
         * @param descriptor    the method's descriptor (see {@link Type Type}).
         */
        protected MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            System.out.println("descriptor = " + descriptor);
            return super.visitAnnotation(descriptor, visible);
        }

        int start;

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            //            Long l = System.currentTimeMillis();
            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            start = newLocal(Type.LONG_TYPE);
            storeLocal(start);
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            //  Long e = System.currentTimeMillis();
//            System.out.println("execute= " + (e - i));
            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            int end = newLocal(Type.LONG_TYPE);
            storeLocal(end);
            getStatic(Type.getType("Ljava/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"));
            newInstance(Type.getType("Ljava/lang/StringBuilder;"));
            dup();//???
            invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"), new Method("<init>", "()V"));
            visitLdcInsn("execute= ");
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
            loadLocal(end);
            loadLocal(start);
            math(SUB, Type.LONG_TYPE);
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(J)Ljava/lang/StringBuilder;"));
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("toString", "()Ljava/lang/String;"));
            invokeVirtual(Type.getType("Ljava/io/PrintStream;"), new Method("println", "(Ljava/lang/String;)V"));
        }
    }
}

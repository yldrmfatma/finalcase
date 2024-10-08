PGDMP      	                |         	   DriveraDB    13.15    16.0 9    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    32769 	   DriveraDB    DATABASE     �   CREATE DATABASE "DriveraDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE "DriveraDB";
                postgres    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false            �           0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    4            �            1259    32899    login_history    TABLE       CREATE TABLE public.login_history (
    login_id integer NOT NULL,
    user_id integer,
    login_time timestamp with time zone NOT NULL,
    logout_time timestamp with time zone,
    ip_address character varying(45),
    device_info character varying(255)
);
 !   DROP TABLE public.login_history;
       public         heap    postgres    false    4            �            1259    32897    login_history_login_id_seq    SEQUENCE     �   CREATE SEQUENCE public.login_history_login_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.login_history_login_id_seq;
       public          postgres    false    4    201            �           0    0    login_history_login_id_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.login_history_login_id_seq OWNED BY public.login_history.login_id;
          public          postgres    false    200            �            1259    32928    otp_verifications    TABLE     W  CREATE TABLE public.otp_verifications (
    otp_id integer NOT NULL,
    user_id integer,
    phone_number character varying(20) NOT NULL,
    otp_code character varying(10) NOT NULL,
    is_verified boolean DEFAULT false,
    expiry_date timestamp with time zone NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);
 %   DROP TABLE public.otp_verifications;
       public         heap    postgres    false    4            �            1259    32926    otp_verifications_otp_id_seq    SEQUENCE     �   CREATE SEQUENCE public.otp_verifications_otp_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.otp_verifications_otp_id_seq;
       public          postgres    false    205    4            �           0    0    otp_verifications_otp_id_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.otp_verifications_otp_id_seq OWNED BY public.otp_verifications.otp_id;
          public          postgres    false    204            �            1259    32943    permissions    TABLE     Y  CREATE TABLE public.permissions (
    permission_id integer NOT NULL,
    user_id integer,
    permission_type character varying(50) NOT NULL,
    granted_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    expires_at timestamp with time zone,
    status character varying(20) DEFAULT 'active'::character varying,
    description text
);
    DROP TABLE public.permissions;
       public         heap    postgres    false    4            �            1259    32941    permissions_permission_id_seq    SEQUENCE     �   CREATE SEQUENCE public.permissions_permission_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE public.permissions_permission_id_seq;
       public          postgres    false    4    207                        0    0    permissions_permission_id_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public.permissions_permission_id_seq OWNED BY public.permissions.permission_id;
          public          postgres    false    206            �            1259    41051    signup_methods    TABLE     �   CREATE TABLE public.signup_methods (
    signup_method_id integer NOT NULL,
    user_id integer,
    method character varying(20) NOT NULL
);
 "   DROP TABLE public.signup_methods;
       public         heap    postgres    false    4            �            1259    41049 #   signup_methods_signup_method_id_seq    SEQUENCE     �   CREATE SEQUENCE public.signup_methods_signup_method_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 :   DROP SEQUENCE public.signup_methods_signup_method_id_seq;
       public          postgres    false    4    211                       0    0 #   signup_methods_signup_method_id_seq    SEQUENCE OWNED BY     k   ALTER SEQUENCE public.signup_methods_signup_method_id_seq OWNED BY public.signup_methods.signup_method_id;
          public          postgres    false    210            �            1259    32912    social_logins    TABLE        CREATE TABLE public.social_logins (
    social_login_id integer NOT NULL,
    user_id integer NOT NULL,
    provider character varying(50) NOT NULL,
    provider_user_id character varying(255) NOT NULL,
    access_token character varying(255),
    refresh_token character varying(255)
);
 !   DROP TABLE public.social_logins;
       public         heap    postgres    false    4            �            1259    32910 !   social_logins_social_login_id_seq    SEQUENCE     �   CREATE SEQUENCE public.social_logins_social_login_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 8   DROP SEQUENCE public.social_logins_social_login_id_seq;
       public          postgres    false    203    4                       0    0 !   social_logins_social_login_id_seq    SEQUENCE OWNED BY     g   ALTER SEQUENCE public.social_logins_social_login_id_seq OWNED BY public.social_logins.social_login_id;
          public          postgres    false    202            �            1259    40987    users    TABLE     �  CREATE TABLE public.users (
    user_id integer NOT NULL,
    username character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    password_hash character varying(255) NOT NULL,
    phone_number character varying(25),
    is_2fa_enabled boolean DEFAULT false NOT NULL,
    method_2fa character varying(20),
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    birth_date date NOT NULL,
    birth_place character varying(100) NOT NULL,
    current_location character varying(100) NOT NULL,
    citizenship character varying(2) NOT NULL,
    consent_date timestamp with time zone NOT NULL,
    consent_type character varying(10) NOT NULL,
    profile_picture_url character varying(255),
    signup_method character varying(20) DEFAULT 'manual_signup'::character varying NOT NULL,
    CONSTRAINT check_phone_number CHECK (((is_2fa_enabled = false) OR (phone_number IS NOT NULL)))
);
    DROP TABLE public.users;
       public         heap    postgres    false    4            �            1259    40985    users_temp_user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_temp_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.users_temp_user_id_seq;
       public          postgres    false    4    209                       0    0    users_temp_user_id_seq    SEQUENCE OWNED BY     L   ALTER SEQUENCE public.users_temp_user_id_seq OWNED BY public.users.user_id;
          public          postgres    false    208            C           2604    32902    login_history login_id    DEFAULT     �   ALTER TABLE ONLY public.login_history ALTER COLUMN login_id SET DEFAULT nextval('public.login_history_login_id_seq'::regclass);
 E   ALTER TABLE public.login_history ALTER COLUMN login_id DROP DEFAULT;
       public          postgres    false    201    200    201            E           2604    32931    otp_verifications otp_id    DEFAULT     �   ALTER TABLE ONLY public.otp_verifications ALTER COLUMN otp_id SET DEFAULT nextval('public.otp_verifications_otp_id_seq'::regclass);
 G   ALTER TABLE public.otp_verifications ALTER COLUMN otp_id DROP DEFAULT;
       public          postgres    false    204    205    205            H           2604    32946    permissions permission_id    DEFAULT     �   ALTER TABLE ONLY public.permissions ALTER COLUMN permission_id SET DEFAULT nextval('public.permissions_permission_id_seq'::regclass);
 H   ALTER TABLE public.permissions ALTER COLUMN permission_id DROP DEFAULT;
       public          postgres    false    207    206    207            N           2604    41054    signup_methods signup_method_id    DEFAULT     �   ALTER TABLE ONLY public.signup_methods ALTER COLUMN signup_method_id SET DEFAULT nextval('public.signup_methods_signup_method_id_seq'::regclass);
 N   ALTER TABLE public.signup_methods ALTER COLUMN signup_method_id DROP DEFAULT;
       public          postgres    false    211    210    211            D           2604    32915    social_logins social_login_id    DEFAULT     �   ALTER TABLE ONLY public.social_logins ALTER COLUMN social_login_id SET DEFAULT nextval('public.social_logins_social_login_id_seq'::regclass);
 L   ALTER TABLE public.social_logins ALTER COLUMN social_login_id DROP DEFAULT;
       public          postgres    false    203    202    203            K           2604    40990    users user_id    DEFAULT     s   ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_temp_user_id_seq'::regclass);
 <   ALTER TABLE public.users ALTER COLUMN user_id DROP DEFAULT;
       public          postgres    false    209    208    209            �          0    32899    login_history 
   TABLE DATA           l   COPY public.login_history (login_id, user_id, login_time, logout_time, ip_address, device_info) FROM stdin;
    public          postgres    false    201   hK       �          0    32928    otp_verifications 
   TABLE DATA           z   COPY public.otp_verifications (otp_id, user_id, phone_number, otp_code, is_verified, expiry_date, created_at) FROM stdin;
    public          postgres    false    205   �K       �          0    32943    permissions 
   TABLE DATA           {   COPY public.permissions (permission_id, user_id, permission_type, granted_at, expires_at, status, description) FROM stdin;
    public          postgres    false    207   �K       �          0    41051    signup_methods 
   TABLE DATA           K   COPY public.signup_methods (signup_method_id, user_id, method) FROM stdin;
    public          postgres    false    211   �K       �          0    32912    social_logins 
   TABLE DATA           z   COPY public.social_logins (social_login_id, user_id, provider, provider_user_id, access_token, refresh_token) FROM stdin;
    public          postgres    false    203   �K       �          0    40987    users 
   TABLE DATA             COPY public.users (user_id, username, email, password_hash, phone_number, is_2fa_enabled, method_2fa, first_name, last_name, birth_date, birth_place, current_location, citizenship, consent_date, consent_type, profile_picture_url, signup_method) FROM stdin;
    public          postgres    false    209   �K                  0    0    login_history_login_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.login_history_login_id_seq', 1, false);
          public          postgres    false    200                       0    0    otp_verifications_otp_id_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.otp_verifications_otp_id_seq', 1, false);
          public          postgres    false    204                       0    0    permissions_permission_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.permissions_permission_id_seq', 1, false);
          public          postgres    false    206                       0    0 #   signup_methods_signup_method_id_seq    SEQUENCE SET     R   SELECT pg_catalog.setval('public.signup_methods_signup_method_id_seq', 1, false);
          public          postgres    false    210                       0    0 !   social_logins_social_login_id_seq    SEQUENCE SET     P   SELECT pg_catalog.setval('public.social_logins_social_login_id_seq', 1, false);
          public          postgres    false    202            	           0    0    users_temp_user_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.users_temp_user_id_seq', 286, true);
          public          postgres    false    208            Q           2606    32904     login_history login_history_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.login_history
    ADD CONSTRAINT login_history_pkey PRIMARY KEY (login_id);
 J   ALTER TABLE ONLY public.login_history DROP CONSTRAINT login_history_pkey;
       public            postgres    false    201            U           2606    32935 (   otp_verifications otp_verifications_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.otp_verifications
    ADD CONSTRAINT otp_verifications_pkey PRIMARY KEY (otp_id);
 R   ALTER TABLE ONLY public.otp_verifications DROP CONSTRAINT otp_verifications_pkey;
       public            postgres    false    205            W           2606    32949    permissions permissions_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_pkey PRIMARY KEY (permission_id);
 F   ALTER TABLE ONLY public.permissions DROP CONSTRAINT permissions_pkey;
       public            postgres    false    207            c           2606    41056 "   signup_methods signup_methods_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.signup_methods
    ADD CONSTRAINT signup_methods_pkey PRIMARY KEY (signup_method_id);
 L   ALTER TABLE ONLY public.signup_methods DROP CONSTRAINT signup_methods_pkey;
       public            postgres    false    211            S           2606    32920     social_logins social_logins_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY public.social_logins
    ADD CONSTRAINT social_logins_pkey PRIMARY KEY (social_login_id);
 J   ALTER TABLE ONLY public.social_logins DROP CONSTRAINT social_logins_pkey;
       public            postgres    false    203            Y           2606    41034 +   permissions unique_permission_type_per_user 
   CONSTRAINT     z   ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT unique_permission_type_per_user UNIQUE (user_id, permission_type);
 U   ALTER TABLE ONLY public.permissions DROP CONSTRAINT unique_permission_type_per_user;
       public            postgres    false    207    207            [           2606    41001    users users_temp_email_key 
   CONSTRAINT     V   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_temp_email_key UNIQUE (email);
 D   ALTER TABLE ONLY public.users DROP CONSTRAINT users_temp_email_key;
       public            postgres    false    209            ]           2606    41003 !   users users_temp_phone_number_key 
   CONSTRAINT     d   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_temp_phone_number_key UNIQUE (phone_number);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT users_temp_phone_number_key;
       public            postgres    false    209            _           2606    40997    users users_temp_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_temp_pkey PRIMARY KEY (user_id);
 ?   ALTER TABLE ONLY public.users DROP CONSTRAINT users_temp_pkey;
       public            postgres    false    209            a           2606    40999    users users_temp_username_key 
   CONSTRAINT     \   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_temp_username_key UNIQUE (username);
 G   ALTER TABLE ONLY public.users DROP CONSTRAINT users_temp_username_key;
       public            postgres    false    209            d           2606    41004 (   login_history login_history_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.login_history
    ADD CONSTRAINT login_history_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 R   ALTER TABLE ONLY public.login_history DROP CONSTRAINT login_history_user_id_fkey;
       public          postgres    false    201    209    2911            f           2606    41014 0   otp_verifications otp_verifications_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.otp_verifications
    ADD CONSTRAINT otp_verifications_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 Z   ALTER TABLE ONLY public.otp_verifications DROP CONSTRAINT otp_verifications_user_id_fkey;
       public          postgres    false    2911    205    209            g           2606    41019 $   permissions permissions_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 N   ALTER TABLE ONLY public.permissions DROP CONSTRAINT permissions_user_id_fkey;
       public          postgres    false    209    2911    207            h           2606    41057 *   signup_methods signup_methods_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.signup_methods
    ADD CONSTRAINT signup_methods_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;
 T   ALTER TABLE ONLY public.signup_methods DROP CONSTRAINT signup_methods_user_id_fkey;
       public          postgres    false    211    209    2911            e           2606    41062 (   social_logins social_logins_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.social_logins
    ADD CONSTRAINT social_logins_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON DELETE CASCADE;
 R   ALTER TABLE ONLY public.social_logins DROP CONSTRAINT social_logins_user_id_fkey;
       public          postgres    false    209    203    2911            �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x��]˒�8v]c�B�	9�U��^VI���@eR�T2�5��Rg����K��[�ƫ�I��G�^<	$�E%�1�G pϹ�c��6�~I�*������7w]�/�������vX���W|�"y��	�%_��ߑ�،<k6�@���$��z���͡3�O	KX�8)'�M���	cNRr����j����}�����?���㮹=tZ�ɤ�G�:ٓ�����ߚ�����%�	��T�iN��k>p�cLQ�$M�d�p�]����4Ú�kZ��]���x���h�b�֏)}�_?`�ʿ6��QR�'�\��d_���/RXE?���A�=���[���@V�{�jrqv\�����h���O��L�d����>Cj��
Y.��6#�����4�A�_>�	y��=Wmd�#Z<�/��鳒���o��/ͭ�Y� +��^n��]]M�/?~�6�U9�'�n0���;���BHaFP��g��"_��]5b>��a� ,e߽�f�������cZ��m��_̄�I�:3�麚�n�+�ç�J��V;�*5Q_~�_lI��/�jq�V�U�c�ȧ��<l{�k!�ј�	��pץ	Yvͱ�i��e/�,qֲ�^}�'4hmw+�II^�����*�(KaQ2�O�1�����a@#7)�Q�)J� z���і|���k�����d0���ݺs��bm����:�,N2�u�ꏂ9ƒ�CT�_�U$϶���&�,o0��//�d�NLɚ/�a�hf��Π���+lJ^�~��w�A������ݡ�?6�'1��ivz.#�������T1�.�	�\�b���m3�`D�U�38�i9�4���_Um���ʠ���oECru��UkT0,��q��ᕊ��Y�� r&�u�?0�1�b�wr.�UA�W�����q��a�J��ٴ$ۇ����;+���P�(��>�>��7����v��QO��Y�W���碫2���pU\�����N�e5GSm���<�?�dJb��d���B'���+~dU�j4qe�q"P� 8=�{�S��	&'|�s�,KȮY5ÍP'��Ŗ;$AI��_`R7���&�� �Տ߇�����k��ƴ]F���/8Z��*�1:�3�>��B�,�.���	y�4��q���+;�����ZaL���CL�������G.M	ڹRtݺ�0'V�S!�tcJ��n������CL�?�v��}�o�,� #���w���p�U�J+ ڈp�2r<�_��K~�]0E�`+��CK�n��F�8��3S����H��N��)��9t�23
�&�ZB��q�����U?�����B:���Mя�a�۾�M
y4�m�,�����'@vb���+���A��X�CsX�j�]`30���
�@�1'Ͼ��a�蟩t�;gYE �kA���G�S�q�̀�����Ԭ�a��f�\��۬&˶Ck�1����`�qϰ�QH�a��c���'��yA��YE/�¡���96Ï�7���2T�`m��a��N��ȧ�����
���*��9����h:�� �A�_�5�R�ߩ��;9#��nw�1����H_�\�㷃�y�h��ۣF�(������1���e��������Ej��DS���07�{剙�/un��io�7R(�?P�0RE?�G�4�Q*�������k�2~a�~pg�����hgA��Ä�������
�n6��w,��9t�*���V��j99��j��atz�$�a���?,�*R��x�Q�YF�0C�bNY^��8��������b�����4M{����'�E��&}s��B4�%?.�(R�`�Æ#�$�G>��5H�I�Pil�	8���ܔ�K�-R��5B3a��A��A#�s�U��z��8m���0*cɏ�a��r7�3iBaU����W6�*:!)�9���lZ1Qƒ�� �b���J+N�g�u�����ϵ)8�**]
�M
zK~p}d���~��l�W��>8`��f`]�7\��X��sX#S�����>r���1�Y4k��4�\x�b������eH_Rz�RƳs��T����0^cf����e(�qr�"4��;=W�#�?<H�Q����ȴ��6�� �i8�j��E����ø��ǰ-��u�<�=[�l��Q��U�]f�~\ud��!=G#��Jt�B���2��ڔ��*�42E�o�jd��3�r��cI�ol�A6a���ȍ�q��T���"�i�d��m����fD��*���;��
�X�^�,V�Y�)S�w�v/+Lы̰�r��݃z�Ʀj�H	���J��y�ܖ'�G���ه�������.V0Nw��{�[�/�ٟ j�mo�Ǳ5��bM�&o������We���\x�(������ovb����[&��f�^��m�#��"|J>�ݩ�/�ĕ���,qi���Y�I���`�V��}#�v6V��[���Of�Ub��z��g�M,uw����]�k�W!p��<}~]4V�&i�m9�K���ڀc)�6�g�w�@.7qLj~K<, �N��=�~M~V�q�1��d��f�o�%�X�wo��MW�OعN���- �� [e��1V#�k���h���B���68���S�Y��/OX%dr���>H�I�аY- 2��}�V5&�Z�^���s�j�<�֞'i��[#{z�*���a��"��L���G�h>jvV����5��*�dh��=�>�X�?a����~�q��8�lʥh��E�$�z�]�W�nc�,+��,{��3�����0ܭ�"�b���P��q&_�}Cފ�*���k�
:��׭)�xmF"�}����+P$-��h��Q1țÂ�`0bc��UP��|��o����g�ށ����w��ui������z�{�uGr������2P�����W�����3Cq,C[=����+H��n, L��P����w�&16�(<3��M�Ly��w%�~[��F�0����Z �����]{��g|�Zf�.�ȇ���s����|Z��*rw3l�!�V��0-^��C�����-�R��a����^}��@yt���|)9-��~t�;h��4w�ۡ�{��Iv��`���U��#�ȠhR��gx��N��G�X5�Q�Ks�-A�`�לL;��d��/�BG��+~ќLUsn1�5�
�x�r3����nP�ʟ�&�Ft�A���M+������!��+���?Uc$_�y�_5�N���8�͞O�tbJ��v��.L��g9H�������q�I����,��]��޽7��$j轌������~OS�C3t��.�B�U��@��1N�\���Y�UVN���V b���!���[^��M�<ePQE�aύzŽ�����f�~Z:��A�`H�������_��ؑB���s�J.JF���n�[��%?>��#����Nl�����5�^
S�&Q�@V�k��;�����u0��BQ��8��G�[˽5��`��@줊M�p�i�U�c4@
M O�����R!MY<�A�c�kP�(I 3����0䐂�����1��\\#n]������4�[�U�3�i<�HM�J�^��p<���B�$z����ݥ	]`�~tV I��ƀ��2 $eA����|�KN+H l��gE�0
���j��Ƽq!�"�`���?`M1zTi�(숁�I��x0�!���9�@c�w| �@�3�r��RFaQ��|/�u$㬷����dl���F�3�j�� �FVW4�R���'�>�#KҚ ڴ=@��Qh�x�{;�Vʩp&�"\��Ѥ"_��o�ۭ[��Njу$H����4�]�������3>���G�\�i3�w�{�J�(	�GIG+���B�b{E�b�R���9�	������=X�<R*y�r�RD�0khg+s�Af�(��t||R�+!���E�ma�|RoPf[�9;�QQ��ndd{���ʬb�TI��R*VQn�/�X�kKF�Y6�ۤ�!����    �a�I-v�$Cz)5�Hw��j��c%��!A�Tsh���0T\�Z�dI�S*��>�P��cG&����V�Уn&rZ�3ɐo���c/��W��V)!��
;n)iAv��>ǃ0��j��I��S*ʹ����_`l%���o����0$SM*�(2Ni�>�@*�Tb�����"�/+r�r'���S(�M���sw�3�'��άC�#�'�$n��~с^s�c�P2$�J�)ҝ�s����#Q����Ӄ0���*��X�Hɐb*E1j�Iz�R��(��q�z�ed���5�г�;�"+�������i��Eޣ�X2gٕB�A��hym�c�Pr��JQ�/�����Ek�U�y~5��_J�_,�)����)���㕇,�*\]
/;s����2ҁ�k�#(9�I���馁�gb4�PP<����C}>�ތPY����	����}�քJmЉ�C)k��X��,dV1v�Y���r�me����UM��!橰�,EhA��c1v�$GΨ����-�1I(OC�C ~���[E��.D¤;��#mԊ6��$7+�Z��EA�i��!4����9�I�6މp�mr��9�E�G��/Mȭ`2�Ϗ��Q��V�!]yIh�0`�Ė���$N���d�Ah�;�"vxԖ_�釣�8H]@1�v���/Mi��E��E�є��VL�z�o��㧻�D���¬��j�+S�U;v��Lj�*rI���/|��1N�47h�#��d'�Y�hfacgT
d�Z��ox������EKNn?�c�An�*��)�Jm��$� iY����`�_9-�0Ce�c�T
����*�%��`g�Ĵ$�(�2�;�R �ԚSD��(.qJ�H�R�i�����5��*��@&i�L"�r�\�A.as�LZ�;��J�JQ!:�'88�'��3�L�N'f��+V�ʱ�+E�@��0,���g0�3��h=gf�Bc��k��3+�'/M,JQQ����Nq�F�O-�F�%c�'`#Cec�UJ�(�1�������[r��i/��Q	�!t���K�;�R2D�y��WPF_Xl�Y��z��������^��j�;�R��NQ��p)Z�U\�̻��v[N�G�����2Ch&�5F����Ɯ��on�N,�7������ub��g5&�Kx)~��!�r�ÁaV�_��e5������\�����J-6���r��j���R��J�lB��{����ހY�IV�#���,��J�\B���@m�����1i2c���%��(�ȆGJ��x�����q�G�bF�y�9%7b��`S�`��)5Q�����Ë�uP��Z��5��9#�#�uK��+R�t<��v���G�)�\��&s^����z�Ӛ�!�
	��c`w؎�>^[����<#���]�4R!�P�!�O��<�YG]c6���|1�����["��H�僼�u�K
?���ryAV�ɑU��He��B"��������P�=����r\xW��.��;����|�V)�-�O�̢��y/7�%?0C����4Uӂ�v�1k��8 ��~t�q$f-����ML���u�|��+�1�4���4�a�
��uv���^��%��
v=g���z��V���0eIY�}_\���<~>g��۴ۃL��B����a�كeXG|��& ���vaJ��==�]�#4�Q#w��:����(0�|df$��4�~f�3�Q�Wvُ��G���&k�F�Q����',��^N�
RZE?.C5��'Ed��,��h9&oJ�r��-�N2�{V�ϰE�l��:��nՎ�+ݎL$��ynI�u*�~C/׌�}l����.�9k���O�\��7|�a����Ww�]�7���g����x~	��xÀF�����G{�&�z�eVя��F�������cOgꎛ?6�����j�\[E?L�!Hj��o������3Ӎ%%���nŤ8rhh��쫩�R������{��&��|��u��c�-ۡ�Lk�8t�ͳ$Q�������oL\b�;�J$d!�]�M��h����LV�&N��������X�Ι-Qh���7������?�)�V@���!��ƊXľ��8�G̓���Hi7���S��>=y@[:h��O��+�{�չbk���m��_n��ESyѲ Mz�T+%�j h� U�<�c[̂2.��P��vh0����Q��a�S��m�>`~��<���8k��P�[�4@�}Y��^�����?6NA��D-��T�t=
�8ۖ88U������O����g�@�	��:��ﺛ����� �d'��|x���,�xŻ�v��a�"��������/���j (s���P���dt���|xh��/c��I�I��@wV�|��|��Gٝ�Z e�d��2���rѻ	f+��d�M�'1�:P��TMk���0Ջ��h�LA�BfY����*9�ʹ�����D�����p�Y������n�Z7�y?y 쐑N�V4��v�a���[����1�z�߷����Ƀ T���<R�T�q�W��O�9�@�9*�*����cW& ���T1�{�R\�!�m 8D�G,���yzTVN5Б�ɥ>`{3J+1J�sV��s��ț�;�>��-;��L}Ħ�a�0F�H��$��'5�iS�>	�u�(�J�Z��gR�N����z�P�1G��b2����T'ڃ֎�C/(<��CL,�T�j�Ҵ@��Qne��|+��$���8��T3r�۾]����HZ�-�$[Ą*$	�z�ǈ�NU^��6M��5��?R��,�$��Arg��Tg�V-3s���Rn�������+@|wmI��W)�Psr��=���%��ڙ�X=��J�̽z���Y���v��z ���:dx)Zۮ1�&zޔ�j�G<�}*̣����A ��N:a�L�n�i�N����8K�vr����Ь�Z��I-�����:������f���c�jr���h�v'�F�/?��:������*��6�i(��Y{�s�X��g�h,�ȩ`:d4�x)Z;����3M��ɹO,��Qn�n=��!#�H��A]۵���|w�e�ܘW.�]�H{׬m��� *�\f���M������^���gJ�nw���X%����Q1&��Ɠx��%�^"'A�^���g&o�G�� 6�������F�`X��E�Rr�3']s�&�:�A �CF:��l�h:\:g�J�q@�}$�����[�N5��!$;�[lo���YU�(��`�G2��Z����e|u� �ա�bL{v;U��ZO8߫�W;��*.�j��Y8�@�"��Ƅ5Te�1��"L�l�.Mj�j���}�鐒�J =�i�A�'��g"Z�L�C�n����L��#��޺���8n4�{��E�8���23���+�c�4y��=�2�?$E���E����;�G�<`uȩL�����L�%�I�D/��n��x��w�V@j�i
>��vH9�����!��t���C��R>�P:�T�w<�\��2=��('� ��GhK�[� L��t�����0�K����4�#���E�>�S�sȨ�ޑLO�?v�R_d���Z�jW�v�O�p:D�3(�}�����Ξr�j��7��0��a,Q�}�P�p����y�D��$��T5GI�Q����y@�P�Nd W��H�
�R<��P�m���`�S�A �CScN�K�|*O�M�ayʒ9B�Q�l�#7�aZ uXJg6x�m'���:g8��9
�)��;��Y̱I-��a�*u��kuq4R�XJjR��%ޡ8�2U'P~�9^�a'��xv6a</.�X&�N��bZ�tjL|�\s�(��<g�d:���W^�J'Ny� ��!)�Amr6��)�3yq	�������h��N��Y��^�k����ss��#�`�mv;k%tZ@u�J'E��c�u���c/.���M��D\s�y�}�G�;5�G����ï��V{��9�����7�� 'OPV�yވ�N\�BZ:?��9�
Z\I�M��'O�z�-/� �  x���Ղ���J)�%n$��S�uHJ'Sx��'& ��А	H�i�2y1۸�4� :U����5���;��Ź��ΑPiJ��C��/�V8z҉>`[�+�G=3_"ds�SVʫ��P̜���D��:y�p�>�&Xi�i,S���ޛ�\���ٯ���m���< V]��,cIUy&�m�ħ]� ^�)�e<[*h�j��� �c*}`��:���I rq��v~t���jF�`̱�ʬ��p����� r%���xGS^��ԪT y&^av<2�����L�1f��nkǤ@Y�����������(#�I�:�9��������oۯ�v-���Y w��r%;���1���A~/�6�+��7�#O�At'>���]��{��g��?������Z�ڽ�x�- d:��Y��3���2Mg�p`R�2�0��^�y+_2�g�"���Ū��vѲ��#���ӭŞ'�촇���ӭ���L�2��11�C��a}��4í
�j j�{�(�Z`}.[�a�[���gΓ�~B;V�	�o��b�T�˧� V��Y����q�\rv���(���rh��"�[ �䖂DT6AM4lne���ߙ&�.��X���Y?4G	�p��Jӻ���0���]9��ŋ|ũ=��J��G��h�vP��:��t��i����E�8Ԩ�#g��.�#���:�)1��z ����1�Xk��^uC�!�:�#>@��f��G�#���"1����
k *��+��9��пϞ�~D��/��lz�V<ή���Snc���g��IXN�9e�ѕ�K�"����ڛ��җ��P5��95�A�dƜI��bq��bg�)<j̅���]»���bZ�<�2��T^Ck�Y'�*ԩ���Y:G�@����xeB~� �6=�vT�!�m
rO���R�	xN�m C\=��u�zR�=�3ی��W�$�w��2ABͽ�̅Z�+�\�0��7��_s��/�����z�|�>-��5���v�} {�f�i�|��f;�3"�^� .��ny{N�܀��x5��U���|���=�'���
XM�nw�����i��_� (�^0j:{�H�(O�K���GM�F���v�G�9«d����͹���I ��-��M��1�,�;��(j���|{��vX����6M�Ȱ2�[�w��bLj~���\ֺ�%���׽�6��-�t�
+�s���f��D����Q 1�v��ܨ<��0>�ՓV��v��i�r7)�}̼��e��n2�@��B����NV�K &Ѧs$���:�;�)�VX5�Q��f��6W�J+F����&�]�`����ٌs�x����6��ܭ`{x�d#�Q�2�m"Ϲ@/7��?�r\�1� �|��� 냁��r��0r�j�v;��=Z�`eM~�NF�� ���i��/89�Xƕ`�9*�J��'�aN= U�-hzbx�őW��h����~;�^>5p礤�{nD�u�>���> �0���$��^�׌{9h�d�D]ϑc���Ҥ�r����ЂJj'��P��p�^�XC�%s䘸�|h�ݵ�~��)���h�Dc�ea���g���92��ȽH�q��X�֩;|�q3
����Y�!4��h�*�Ͱ���������|Jf)�g2q��'h>j�3�J��MV��(r���5�i-�3=��!�:����`⺆��b=E����Pa��?��O���D     
package cn.org.rapid_framework.web.httpinclude;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.tuckey.web.MockRequestDispatcher;

public class HttpIncludeTest extends TestCase {
	String cookie = "_javaeye3_session_=BAh7BzoMdXNlcl9pZGkCxEw6D3Nlc3Npb25faWQiJTg2NTRkNDgxNjhiYzhiY2RhODg1N2M3OTBjMGNkYTI5--a2a5c1d58579038336b581bab0ad2b53b4526ca5";
	public void test_remote_with_cookie() {
		MockHttpServletResponse response = new MockHttpServletResponse();
		response.setCharacterEncoding("UTF-8");
		MockHttpServletRequest request = new MockHttpServletRequest();
		HttpInclude http = new HttpInclude(request, response);
		System.out.println(http.include("http://www.163.com"));
	}
	
	boolean includeExecuted = false;
	public void test_local_write_date_with_output_stream() throws UnsupportedEncodingException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		response.setCharacterEncoding("UTF-8");
		MockHttpServletRequest request = new MockHttpServletRequest(){
			@Override
			public RequestDispatcher getRequestDispatcher(final String path) {
				return new MockRequestDispatcher(path) {
					@Override
					public void include(ServletRequest servletRequest,ServletResponse servletResponse)throws ServletException, IOException {
						response.setIncludedUrl(path);
						new PrintStream(servletResponse.getOutputStream()).append("test_local_write_date_with_output_stream").flush();
//						super.include(servletRequest, servletResponse);
						includeExecuted = true;
					}
				};
			}
		};
		HttpInclude http = new HttpInclude(request, response);
		
		String includeContent = http.include("/userinfo/blog.htm");
		System.out.println(includeContent);
		System.out.println(response.getIncludedUrl());
		
		assertTrue(includeExecuted);
		assertEquals(includeContent,"test_local_write_date_with_output_stream");
		assertEquals(response.getIncludedUrl(),"/userinfo/blog.htm");
	}
	
	public void test_local_write_date_with_write() throws UnsupportedEncodingException {
		final MockHttpServletResponse response = new MockHttpServletResponse();
		response.setCharacterEncoding("UTF-8");
		MockHttpServletRequest request = new MockHttpServletRequest(){
			@Override
			public RequestDispatcher getRequestDispatcher(final String path) {
				return new MockRequestDispatcher(path) {
					@Override
					public void include(ServletRequest servletRequest,ServletResponse servletResponse)throws ServletException, IOException {
						response.setIncludedUrl(path);
						servletResponse.getWriter().append("test_local_write_date_with_write");
//						super.include(servletRequest, servletResponse);
						includeExecuted = true;
					}
				};
			}
		};
		HttpInclude http = new HttpInclude(request, response);
		
		String includeContent = http.include("/userinfo/blog.htm");
		System.out.println(includeContent);
		System.out.println(response.getIncludedUrl());
		
		assertTrue(includeExecuted);
		assertEquals(includeContent,"test_local_write_date_with_write");
		assertEquals(response.getIncludedUrl(),"/userinfo/blog.htm");
	}
	
	public void test_local_write_date_with_call_writer_and_outputstream()
                                                                         throws UnsupportedEncodingException {
        final MockHttpServletResponse response = new MockHttpServletResponse();
        response.setCharacterEncoding("UTF-8");
        MockHttpServletRequest request = new MockHttpServletRequest() {
            @Override
            public RequestDispatcher getRequestDispatcher(final String path) {
                return new MockRequestDispatcher(path) {
                    @Override
                    public void include(ServletRequest servletRequest,
                                        ServletResponse servletResponse)
                                                                        throws ServletException,
                                                                        IOException {
                        response.setIncludedUrl(path);
                        servletResponse.getWriter().append(
                            "test_local_write_date_with_write");
                        servletResponse.getOutputStream().write('c');
                    }
                };
            }
        };
        HttpInclude http = new HttpInclude(request, response);
        try {
            String includeContent = http.include("/userinfo/blog.htm");
            fail();
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains(
                "getWriter() has already been called for this response"));
        }
    }
	
	   public void test_local_write_date_with_call_outputstream_and_writer()
                                                                         throws UnsupportedEncodingException {
        final MockHttpServletResponse response = new MockHttpServletResponse();
        response.setCharacterEncoding("UTF-8");
        MockHttpServletRequest request = new MockHttpServletRequest() {
            @Override
            public RequestDispatcher getRequestDispatcher(final String path) {
                return new MockRequestDispatcher(path) {
                    @Override
                    public void include(ServletRequest servletRequest,
                                        ServletResponse servletResponse)
                                                                        throws ServletException,
                                                                        IOException {
                        response.setIncludedUrl(path);
                        servletResponse.getOutputStream().write('c');
                        servletResponse.getWriter().append(
                            "test_local_write_date_with_write");
                    }
                };
            }
        };
        HttpInclude http = new HttpInclude(request, response);
        try {
            String includeContent = http.include("/userinfo/blog.htm");
            fail();
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains(
                "getOutputStream() has already been called for this response"));
        }
    }
}
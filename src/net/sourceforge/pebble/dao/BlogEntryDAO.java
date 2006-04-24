/*
 * Copyright (c) 2003-2006, Simon Brown
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in
 *     the documentation and/or other materials provided with the
 *     distribution.
 *
 *   - Neither the name of Pebble nor the names of its contributors may
 *     be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.sourceforge.pebble.dao;

import net.sourceforge.pebble.domain.BlogEntry;
import net.sourceforge.pebble.domain.DailyBlog;
import net.sourceforge.pebble.domain.Blog;

import java.util.Collection;
import java.util.List;

public interface BlogEntryDAO {

  /**
   * Stores the specified blog entry.
   *
   * @param blogEntry   the blog entry to store
   * @throws PersistenceException   if something goes wrong storing the entry
   */
  public void store(BlogEntry blogEntry) throws PersistenceException;

  /**
   * Removes the specified blog entry.
   *
   * @param blogEntry   the blog entry to remove
   * @throws PersistenceException   if something goes wrong removing the entry
   */
  public void remove(BlogEntry blogEntry) throws PersistenceException;

  /**
   * Gets the YearlyBlogs that the specified root blog is managing.
   *
   * @param rootBlog    the owning Blog instance
   * @throws  PersistenceException    if the yearly blogs cannot be loaded
   */
  public List getYearlyBlogs(Blog rootBlog) throws PersistenceException;

  /**
   * Loads the blog entries for a given daily blog.
   *
   * @param dailyBlog   the DailyBlog instance
   * @return  a List of BlogEntry instances
   * @throws  net.sourceforge.pebble.dao.PersistenceException    if blog entries cannot be loaded
   */
  public List getBlogEntries(DailyBlog dailyBlog) throws PersistenceException;

  /**
   * Loads the draft blog entries for a given blog.
   *
   * @param blog    the owning Blog instance
   * @return  a List of BlogEntry instances
   * @throws  net.sourceforge.pebble.dao.PersistenceException    if blog entries cannot be loaded
   */
  public Collection getDraftBlogEntries(Blog blog) throws PersistenceException;

  /**
   * Loads the blog entry templates for a given blog.
   *
   * @param blog    the owning Blog instance
   * @return  a List of BlogEntry instances
   * @throws  net.sourceforge.pebble.dao.PersistenceException    if blog entries cannot be loaded
   */
  public Collection getBlogEntryTemplates(Blog blog) throws PersistenceException;

  /**
   * Loads the static pages for a given blog.
   *
   * @param blog    the owning Blog instance
   * @return  a List of BlogEntry instances
   * @throws  net.sourceforge.pebble.dao.PersistenceException    if blog entries cannot be loaded
   */
  public Collection getStaticPages(Blog blog) throws PersistenceException;

}
